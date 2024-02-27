package com.nizar.SahTech.mobileapp.service;
import org.springframework.stereotype.Service;
import java.util.List;
import com.nizar.SahTech.mobileapp.entity.Announcement;
import com.nizar.SahTech.mobileapp.repository.Pubrepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AppService {
    private final Pubrepository pubrepository;
    public  Announcement AddPublication() {
        Announcement announcement =new Announcement();
        announcement.setTitle(announcement.getTitle());
        announcement.setDescription(announcement.getDescription());
        announcement.setImage(announcement.getImage());
        announcement.setDoctorName(announcement.getDoctorName());
        return announcement;
    }
    public List<Announcement> GetPublication(){
        return  pubrepository.findByisAcceptedTrue(); 
    }
  
}
