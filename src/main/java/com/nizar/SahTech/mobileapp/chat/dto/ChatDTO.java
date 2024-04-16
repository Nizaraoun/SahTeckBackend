package com.nizar.SahTech.mobileapp.chat.dto;

import lombok.Data;
@Data
public class ChatDTO {
    private String message;
    private String doctorId;
    private String userId;
    private String conversationId;
    private byte[] image;
    

}
