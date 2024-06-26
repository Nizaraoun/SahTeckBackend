package com.nizar.SahTech.doctor.dto;


import lombok.Data;

@Data
public class PublicationDTO {
    private String title;
    private String description;
    private String image;
    private String doctorId;
    private String date;
    private Boolean isAccepted;
}
