package com.nizar.SahTech.mobileapp.feed;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "comments") // Map to the "comments" table in the database
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "post_id")
    private Long postId;

    @Lob // Use Lob annotation for large text fields
    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private String createdAt;
}
