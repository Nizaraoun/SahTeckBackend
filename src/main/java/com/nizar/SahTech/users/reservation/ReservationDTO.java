package com.nizar.SahTech.users.reservation;
import lombok.Data;
@Data
public class ReservationDTO {
    private String jour;
    private String heure;
    private String username;
    private String doctorname;
    private String id_patient;
    private String id_praticien;
}