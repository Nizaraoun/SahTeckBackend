package com.nizar.SahTech.users.dto;
import lombok.Data;
@Data
public class ReservationDTO {
    private String jour;
    private String heure;
    private String username;
    private String doctorname;
    private Long id_patient;
    private Long id_praticien;
}
