package com.nizar.SahTech.admin.report;

import org.springframework.stereotype.Service;

import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.doctor.repository.DoctorRepo;
import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;
import com.nizar.SahTech.users.Auth.Otp.OtpRequest;
import com.nizar.SahTech.users.Auth.Otp.SmsService;

import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.print.Doc;
import java.util.ArrayList;


@Service
@AllArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final DoctorRepo doctorRepository;
    private final UserRepository patientRepository;
    private final SmsService smsService;

    public String sendReport(reportDTO reportDTO) {
        LocalDateTime createdAt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedCreatedAt = createdAt.format(formatter);
        report report = new report();
        report.setReportSubject(reportDTO.getReport());
        report.setSenderId(reportDTO.getSenderid());
        report.setRole(reportDTO.getRole());
        report.setStatus(false);
        report.setDate(formattedCreatedAt);
        reportRepository.save(report);
        return "Report sent successfully";
    }

    // responce report
    public String responceReport(reportDTO reportDTO) {
        Optional<report> report = reportRepository.findById(reportDTO.getId());
       try {
        if (report.isPresent()) {
            if (report.get().getRole().equals("doctor")) {
                Optional<DoctorEntity> doctor = doctorRepository.findById(report.get().getSenderId());
                smsService.SendMail(new OtpRequest(doctor.get().getUsername(),  doctor.get().getEmail(), reportDTO.getResponse(), null));
                 return "Report sent successfully";
                
            } else if (report.get().getRole().equals("patient")) {
                Optional<UserEntity> patient = patientRepository.findById(report.get().getSenderId());
                smsService.SendMail(new OtpRequest(patient.get().getUsername(), patient.get().getEmail(), reportDTO.getResponse(), null));
                return "Report sent successfully";
                
            } 
        } else {
            return "Report not found";
        }
       } catch (Exception e) {
              return "Error";
       }
        return "Error";
    }

    public List<reportDTO> getAllReports() {
        List<report> reports = reportRepository.findAll();
        List<reportDTO> reportDTOs = new ArrayList<>();        
        try {
            if (!reports.isEmpty()) {
                for (report report : reports) {
                    reportDTO reportDTO = new reportDTO();
                    if (report.getRole().equals("patient")) {
                        Optional<UserEntity> patient = patientRepository.findById(report.getSenderId());
                        if (patient.isPresent()) {
                            reportDTO.setSendername(patient.get().getEmail());
                            reportDTO.setSenderimage(patient.get().getImage());
                        }
                    } else if (report.getRole().equals("doctor")) {
                        Optional<DoctorEntity> doctor = doctorRepository.findById(report.getSenderId());
                        if (doctor.isPresent()) {
                            reportDTO.setSendername(doctor.get().getEmail());
                            reportDTO.setSenderimage(doctor.get().getImage());
                        }
                    }
    
                    reportDTO.setReport(report.getReportSubject());
                    reportDTO.setSenderid(report.getSenderId());
                    reportDTO.setStatus(report.isStatus());
                    reportDTO.setDate(report.getDate());
                    reportDTO.setId(report.getReportId());
                    reportDTO.setRole(report.getRole());
                    reportDTOs.add(reportDTO);
                    
                    System.out.println(reportDTOs);
                }
            } else {
                System.out.println("No reports found here");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return reportDTOs;
    }
    
        

 
}
