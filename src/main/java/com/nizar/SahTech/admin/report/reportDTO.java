package com.nizar.SahTech.admin.report;

import lombok.Data;

@Data
public class reportDTO {
    private long id;
    private String report;
    private String senderid;
    private String role;
    private String date;
    private String response;
    private boolean status;
    private String senderimage;
    private String sendername;
    
}
