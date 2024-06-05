package com.nizar.SahTech.mobileapp.chat;

import lombok.Data;
@Data
public class ChatDTO {
    
    private String message;
    private String doctorId;
    private String doctorName;
    private String userId;
    private String userName;
    private String conversationId;
    private String image;
    private String lastmsg;
}
