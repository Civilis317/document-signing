package net.playground.documentsigningservice.model;

import lombok.Data;

@Data
public class DocumentSigningRequest {
    private String filename;
    private String contentType;
    private String content;
}
