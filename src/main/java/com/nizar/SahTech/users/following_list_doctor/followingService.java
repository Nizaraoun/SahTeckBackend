package com.nizar.SahTech.users.following_list_doctor;
import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap.Option;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.doctor.repository.DoctorRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class followingService {
        private final followingRepository followingRepository; // Assuming you have a repository for users
        private final DoctorRepo doctorRepo; // Assuming you have a repository for doctors 
     
    public ResponseEntity<?> addfollow(String userid, String id_doctor) {
        Optional<followinglist> following = followingRepository.findById(userid);
        Optional<DoctorEntity> doctor = doctorRepo.findById(id_doctor);
        try {
            if (doctor.isPresent()) {
            if (following.isPresent()) {
                boolean isFollowing = false;
                ResponseEntity<Boolean> response = is_followed(userid, id_doctor);
                if (response != null && response.getBody() != null) {
                    isFollowing = response.getBody();
                }
                if (isFollowing) {
                    return ResponseEntity.badRequest().body("Doctor already added");
                }
              else {
                List<String> doctorIds = new ArrayList<>();
                byte[] doctorIdBytes = following.get().getDoctorId();
                String doctorIdString = new String(doctorIdBytes);
                String[] doctorIdLines = doctorIdString.split("\n");
                for (String line : doctorIdLines) {
                    doctorIds.add(line);
                }
                doctorIds.add(id_doctor);
                StringBuilder newDoctorIds = new StringBuilder();
                for (String id : doctorIds) {
                    newDoctorIds.append(id).append("\n");
                }
                byte[] newDoctorIdBytes = newDoctorIds.toString().getBytes();
                following.get().setDoctorId(newDoctorIdBytes);
                followingRepository.save(following.get());
                doctor.get().setFollowers(doctor.get().getFollowers() + 1);
                doctorRepo.save(doctor.get());
                return ResponseEntity.ok("Doctor added successfully");
            }
            } else {
                followinglist following1 = new followinglist();
                following1.setDoctorId(id_doctor.getBytes());
                following1.setUserId(userid);
                doctor.get().setFollowers(doctor.get().getFollowers() + 1);
                doctorRepo.save(doctor.get());
                followingRepository.save(following1);
                return ResponseEntity.ok("Doctor added successfully");
            }}
            else {
                return ResponseEntity.badRequest().body("Doctor not found");
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add doctor: " + e.getMessage());
        }
    }

        // public ResponseEntity<?> deletefollowing(String id ) {
        //     Optional<followinglist> following = followingRepository.findById(id);
        //     try {
        //         if (following.isPresent()) {
        //             followingRepository.delete(following.get());
        //             return ResponseEntity.ok("Doctor deleted successfully");
        //         }
        //         return ResponseEntity.ok("Doctor not found");
        //     } catch (Exception e) {
        //         return ResponseEntity.badRequest().body("Failed to delete the doctor: " + e.getMessage());
        //     }
        // }

//        Get the list of doctors that the user is following

        public ResponseEntity<Boolean> is_followed(String userId , String doctorId) {
            Optional<followinglist> following = followingRepository.findById(userId);
            try {
                if (following.isPresent()) {
                    List<String> doctorIds = new ArrayList<>();
                    byte[] doctorIdBytes = following.get().getDoctorId();
                    String doctorIdString = new String(doctorIdBytes);
                    String[] doctorIdLines = doctorIdString.split("\n");
                    for (String line : doctorIdLines) {
                        doctorIds.add(line);
                    }
                    if (doctorIds.contains(doctorId)) {
                        return ResponseEntity.ok(true);
                    }
                    
                    return ResponseEntity.ok(false);
           
            
            } }catch (Exception e) {
            
            }
            return ResponseEntity.ok(false);
        }
         }
        

