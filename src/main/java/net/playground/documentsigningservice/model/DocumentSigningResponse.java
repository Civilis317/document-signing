package net.playground.documentsigningservice.model;

import lombok.Data;

import java.util.Date;

@Data
public class DocumentSigningResponse {
    private String filename;
    private String contentType;
    private String content;
    private Date signingDate;
    private String status;
}
