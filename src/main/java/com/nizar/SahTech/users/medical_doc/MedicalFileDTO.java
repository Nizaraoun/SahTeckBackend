package com.nizar.SahTech.users.medical_doc;
import lombok.Data;

@Data
public class MedicalFileDTO {
    private String id;
    private String name;
    private byte[]  file;
    private String txt;
    private Long userId;
}
