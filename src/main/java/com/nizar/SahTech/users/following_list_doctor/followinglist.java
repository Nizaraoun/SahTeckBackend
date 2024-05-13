package com.nizar.SahTech.users.following_list_doctor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data

@Table(name = "following_list_doctor")
@Entity
public class followinglist {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Lob // Use Lob annotation for large text fields
    @Column(name = "doctor_id")
    private byte[] doctorId;
}
