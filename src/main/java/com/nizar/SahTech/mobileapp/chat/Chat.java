package com.nizar.SahTech.mobileapp.chat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Table(name = "chat_table")
@Entity
public class Chat {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "chat_id")
    private String id;   
    @Lob
    @Column(name = "messages", length = 1000000)
    private byte[] message;
    @Column(name = "doctor_id")
    private String doctorId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "last_message")
    private String lastmsg;
}