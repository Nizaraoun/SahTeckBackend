package com.nizar.SahTech.mobileapp.service;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList; // Import the ArrayList class

import java.util.List;
import java.util.Optional;

import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.doctor.repository.DoctorRepo;
import com.nizar.SahTech.mobileapp.entity.Announcement;
import com.nizar.SahTech.mobileapp.entity.Rating;
import com.nizar.SahTech.mobileapp.repository.Pubrepository;
import com.nizar.SahTech.mobileapp.repository.Ratingrepository;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AppService {
    private final Pubrepository pubrepository;
    private final Ratingrepository ratingrepository;
    private final DoctorRepo doctorRepo;


    //add publication to the app user
    public  Announcement AddPublication() {
        Announcement announcement =new Announcement();
        announcement.setTitle(announcement.getTitle());
        announcement.setDescription(announcement.getDescription());
        announcement.setImage(announcement.getImage());
        announcement.setDoctorName(announcement.getDoctorName());
        return announcement;
    }


    //Get all publication to the app user
    public List<Announcement> GetPublication(){
        return  pubrepository.findByisAcceptedTrue(); 
    }

//Get all doctors to the app user
    public List<DoctorEntity> findallDoctor() {
        List<DoctorEntity> list =doctorRepo.findAll();
        list.removeIf(doctor -> !doctor.getIsActive());
        list.forEach(doctor -> {
            doctor.setPassword(null);
            doctor.setIsActive(null);
            doctor.setRoles(null);
            doctor.setCreationDate(null);
        });

        return list;
    }

 //Get all doctors recommendation 
     


 public List<DoctorEntity> GetRecommended_Doctors() {
    List<Long> idList = ratingrepository.findTop15DoctorIdsByRatingDesc();
    List<DoctorEntity> targetList = new ArrayList<>();
    try {
        if (idList.isEmpty()==false) {
             // Populate targetList with DoctorEntity objects corresponding to the ids in idList
             for (Long id : idList) {
                Optional<DoctorEntity> doctorOptional = doctorRepo.findById(id);
                if (doctorOptional.isPresent()) {
                    DoctorEntity doctor = doctorOptional.get();
                    doctor.setPassword(null);
                    doctor.setIsActive(null);
                    doctor.setRoles(null);
                    doctor.setCreationDate(null);
                    targetList.add( doctor);
                }
            }
            return targetList;
        }
        else {
            return null;  
        }
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage()   );
        return null;
    }
}

    //Add rating to the doctor 
    public ResponseEntity <String>   AddRating(Long id ,int rate) {
         Optional<Rating> rating=  ratingrepository.findByDoctorId(id);
         Optional<DoctorEntity> doctor=  doctorRepo.findById(id);


         try {
            if (rating.isPresent()) {
                doctor.get().setRating((rating.get().getRate()+rate) / (rating.get().getNumberOfRating()+1));
               rating.get().setRate((rating.get().getRate()+rate) / (rating.get().getNumberOfRating()+1));
               rating.get().setNumberOfRating(rating.get().getNumberOfRating()+1);
               ratingrepository.save(rating.get()); 
               doctorRepo.save(doctor.get());

            return ResponseEntity.ok("Rating added successfully");   
            }
            else {
             Rating newRate = new Rating();
                doctor.get().setRating(doctor.get().getRating()+rate);
                newRate.setRate(rate);
                newRate.setDoctorId(id);
                newRate.setNumberOfRating(1);
                  ratingrepository.save(newRate);
                  doctorRepo.save(doctor.get());
                return  ResponseEntity.ok("Rating added successfully");  
                
            }
         } catch (Exception e) {
             return ResponseEntity.badRequest().body("Failed to add rating: " + e.getMessage());
         }
       
    }

}
