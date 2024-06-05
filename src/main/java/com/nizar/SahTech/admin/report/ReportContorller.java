package com.nizar.SahTech.admin.report;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/report")
public class ReportContorller {
    private final ReportService reportService;
    private final ReportRepository reportRepository;

    @PostMapping("/send-report")
    public String sendReport(@RequestBody reportDTO reportDTO) {
        return  reportService.sendReport(reportDTO);
           
    }
    
    @GetMapping("/get-all-reports")
    public List<reportDTO> getAllReports() {
        return reportService.getAllReports();
    }
    @PostMapping("/responce-report")
    public String responceReport(@RequestBody reportDTO reportDTO) {
        return  reportService.responceReport(reportDTO);
           
    }
}
