package net.playground.documentsigningservice.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.playground.documentsigningservice.config.KeyStoreConfig;
import net.playground.documentsigningservice.config.SignatureConfig;
import net.playground.documentsigningservice.exception.ApplicationException;
import net.playground.documentsigningservice.model.DocumentSigningRequest;
import net.playground.documentsigningservice.model.DocumentSigningResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class SigningService {

    private final KeyStoreConfig keyStoreConfig;
    private final SignatureConfig signatureConfig;

    private KeyStore keyStore;

    @PostConstruct
    private void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(keyStoreConfig.getKeystorePath()), keyStoreConfig.getKeystorePwd().toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public DocumentSigningResponse processSigningRequest(DocumentSigningRequest request) {
        DocumentSigningResponse response = new DocumentSigningResponse();
        response.setFilename(request.getFilename());
        response.setContent(signDocument(request.getContent()));
        response.setContentType(request.getContentType());
        response.setStatus("signed");
        response.setSigningDate(new Date());
        return response;
    }

    private String signDocument(String base64) {
        try {
            InputStream document = new ByteArrayInputStream(Base64.getDecoder().decode(base64));

            // Load Bouncy Castle Provider
            BouncyCastleProvider provider = new BouncyCastleProvider();
            Security.addProvider(provider);

            PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyStoreConfig.getKeypairAlias(), keyStoreConfig.getKeypairPwd().toCharArray());
            Certificate[] chain = keyStore.getCertificateChain(keyStoreConfig.getKeypairAlias());

            PdfReader reader = new PdfReader(document);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            PdfStamper stamper = PdfStamper.createSignature(reader, baos, '\0');
            PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
            appearance.setSignatureCreator(signatureConfig.getCreator());
            appearance.setReason(signatureConfig.getReason());
            appearance.setLocation(signatureConfig.getLocation());

            appearance.setVisibleSignature(new Rectangle(800, 800, 800, 800), 1, "Signature");

            // Create the signature
            ExternalSignature externalSignature = new PrivateKeySignature(privateKey, DigestAlgorithms.SHA256, provider.getName());
            ExternalDigest externalDigest = new BouncyCastleDigest();
            MakeSignature.signDetached(appearance, externalDigest, externalSignature, chain, null, null, null, 0, MakeSignature.CryptoStandard.CMS);

            stamper.close();

            return Base64.getEncoder().encodeToString(baos.toByteArray());

        } catch (IOException | DocumentException | GeneralSecurityException e) {
            throw new ApplicationException(e.getMessage(), e);
        }

    }

}
