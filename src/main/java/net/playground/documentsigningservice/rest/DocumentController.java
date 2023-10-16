package net.playground.documentsigningservice.rest;

import lombok.extern.slf4j.Slf4j;
import net.playground.documentsigningservice.model.DocumentSigningRequest;
import net.playground.documentsigningservice.model.DocumentSigningResponse;
import net.playground.documentsigningservice.model.InfoResponse;
import net.playground.documentsigningservice.service.SigningService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class DocumentController extends AbstractController {

    private final SigningService signingService;

    public DocumentController(SigningService signingService) {
        this.signingService = signingService;
    }

    @GetMapping("/info")
    public @ResponseBody InfoResponse informationEndpoint() {
        InfoResponse response = new InfoResponse();
        response.setDatetime(new Date());
        response.setDescription("Document signing service");
        response.setStatus("online");
        response.setVersion("1.0.0-SNAPSHOT");
        return response;
    }

    @PostMapping("/sign")
    public @ResponseBody DocumentSigningResponse signDocument (@RequestBody DocumentSigningRequest request) {
        // todo: validate request
        return signingService.processSigningRequest(request);
    }

}
