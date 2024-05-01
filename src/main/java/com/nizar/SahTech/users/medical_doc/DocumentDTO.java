package com.nizar.SahTech.users.medical_doc;

import lombok.Data;
@Data
public class DocumentDTO {
    private int number;
    private String description;
    private Long userId;
    private String code;
}
