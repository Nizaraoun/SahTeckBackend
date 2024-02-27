package com.nizar.SahTech.users.dto;



import lombok.Data;
@Data
public class MedicalFileDTO {
    private Long id;
    private String name;
    private String description;
    private String file;
    private Long userId;
}
