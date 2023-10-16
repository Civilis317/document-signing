package net.playground.documentsigningservice.model;

import lombok.Data;

import java.util.Date;

@Data
public class InfoResponse {

    private String description;
    private String version;
    private String status;
    private Date datetime;
}
