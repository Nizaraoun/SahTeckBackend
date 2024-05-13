package com.nizar.SahTech.mobileapp.feed;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import java.sql.Timestamp;


@Data
@Table(name = "feed_table")
@Entity
public class Feed {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;
    @Column(name = "user_id")
    private String userId;
    @Lob // Use Lob annotation for large text fields
    @Column(name = "content")
    private String content;
    @Column(name = "comment_count")
    private Long commentcount;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "user_Role")
    private String userRole;
    @Column(name = "Anonymous")
    private boolean anonymous;
}
