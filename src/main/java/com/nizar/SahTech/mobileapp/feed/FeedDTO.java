package com.nizar.SahTech.mobileapp.feed;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class FeedDTO {
    Long postId;
    String userId;
    String content; 
    String commentId;
    String commentContent;
    Long commentCount;
    String role;
    String SenderImg;
    String SenderName;
    String createdAt;
    boolean anonymous;
}
