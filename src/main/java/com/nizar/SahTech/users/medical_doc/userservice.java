package com.nizar.SahTech.users.medical_doc;
import java.util.Optional;
import java.security.Principal;

import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap.Option;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nizar.SahTech.users.Auth.UserDTO;
import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;
import java.util.List;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class userservice {
    private final UserRepository userRepository; // Assuming you have a repository for users
private final FilesRepository docFileRepository; // Assuming you have a repository for medical files
private final DocRepository docRepository; // Assuming you have a repository for documents

   
// this method is used to save the profile image for the user
public ResponseEntity<String> saveImageForUser(UserDTO imageData, Principal connectedUser) {
    Optional<UserEntity> userOptional = userRepository.findByUsername(connectedUser.getName());
    if (userOptional.isPresent()) {
        UserEntity user = userOptional.get();
        try {
            // Update the image data for the user
            user.setImage(imageData.getImage());
            userRepository.save(user);
            return ResponseEntity.ok("Image updated successfully for user with ID: " + user.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update image: " + e.getMessage());
        }
    } else {
        return ResponseEntity.badRequest().body("User not found");
    }
}

  // this method is used to save the medical file for the user

public ResponseEntity<String> saveMedicalFileForUser( Principal connecteduser ,MedicalFileDTO medicalFileDTO )  throws IOException{
    Optional<UserEntity> user = userRepository.findByUsername(connecteduser.getName());
    Optional<MedicalFile> files = docFileRepository.findById(medicalFileDTO.getId());
    Optional<Document> document = docRepository.findById(medicalFileDTO.getId());
    byte[] bytes = medicalFileDTO.getTxt().getBytes();

try {
    MedicalFile file = files.get();
    if (file != null ){
        byte[] existingData = file.getFile();
        byte[] combinedData = concatenateByteArrays(existingData, bytes ,document.get().getNumber()+1);
        file.setFile(combinedData);
        document.get().setNumber(document.get().getNumber() + 1);
        docRepository.save(document.get());
        docFileRepository.save(file);
        return ResponseEntity.ok("Medical File updated successfully for user with ID: " + user.get().getId());
    }
    else {
        return ResponseEntity.badRequest().body("Medical File not found for user with ID: " + user.get().getId());
    }
    
 
} catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload Medical File: " + e.getMessage());
}

}
// this method is used to get the profile image for the user
public ResponseEntity<?> getImageForUser(Principal connecteduser) {
    Optional<UserEntity> user = userRepository.findByUsername(connecteduser.getName());
    try {
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getImage());
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get image: " + e.getMessage());
    }
    return ResponseEntity.badRequest().body("User not found");}

// this method is used to get the document for the user
public List<Document> getMedicalDocForUser(Principal connecteduser) {
    Optional<UserEntity> user = userRepository.findByUsername(connecteduser.getName());
    
    List<Document> document = docRepository.findByUserId(user.get().getId());
    try {
        if (document != null) {
            return document;
        }
    } catch (Exception e) {
         
    }
    return null;
}
// this method is used to add the document for the user

public ResponseEntity<?> addMedicalDocForUser(DocumentDTO documentDTO, Principal connecteduser) {
    Optional<UserEntity> user = userRepository.findByUsername(connecteduser.getName());
    Optional<Document> document = docRepository.findByDescription(documentDTO.getDescription());

    Document doc = new Document();
MedicalFile file = new MedicalFile();
byte[] bytes = "".getBytes();


    try {
        if (!document.isPresent() && documentDTO.getDescription() != null) {
            doc.setDescription(documentDTO.getDescription());
            doc.setNumber(0);
            doc.setUserId(user.get().getId());
            docRepository.save(doc);
            file.setId(doc.getId());
            file.setName(user.get().getEmail());
            file.setUserId(user.get().getId());
            file.setFile(bytes);

            docFileRepository.save(file);
            return ResponseEntity.ok("Medical Doc added successfully for user with ID: " + user.get().getId());
          
        }
        
      
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add Medical Doc: " + e.getMessage());
    }
    return ResponseEntity.badRequest().body("Document already exists for user with ID: " + user.get().getId());
}

// this method is used to get the medical file for the user
//public ResponseEntity<?> getMedicalFileForUser(Principal connecteduser) {
  //  Optional<UserEntity> user = userRepository.findByUsername(connecteduser.getName());
    //try {
      //  if (user.isPresent()) {
        //    return ResponseEntity.ok(docFileRepository.findBydocId(user.get().getId()));
        //}
    //} catch (Exception e) {
     //   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get Medical File: " + e.getMessage());
   // }
    //return ResponseEntity.badRequest().body("User not found");
//}

// Helper method to concatenate two byte arrays
private byte[] concatenateByteArrays(byte[] first, byte[] second  , int imageNumber) {
    String str = "Image"+imageNumber+":"+"\n";
    byte[] txt = str.getBytes();

    byte[] result = new byte[first.length + txt.length +second.length];
    System.arraycopy(first, 0, result, 0, first.length);
    System.arraycopy(txt, 0, result, first.length, txt.length);
    System.arraycopy(second, 0, result, first.length + txt.length, second.length);
    
    return result;
}

// public List<MedicalFile> getMedicalFileForUser(Principal connecteduser , Long docId) {
//     Optional<UserEntity> user = userRepository.findByUsername(connecteduser.getName());
//     Optional<MedicalFile> files = docFileRepository.findById(docId);
//     try {
//         MedicalFile file = new MedicalFile();
//         if (files.isPresent()) {
//           String str = new String(files.get().getFile());
//           for (int i = 0; i < str.length(); i++) {
//               if (str.charAt(i) == ' ') {
//                     file.setId(files.get().getId());
//                     file.setName(files.get().getName());
//                     file.setUserId(files.get().getUserId());
//                     file.setFile(files.get().getFile());
//                     return file;
//               }

//         }
    
//     }
//     } catch (Exception e) {
//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get Medical File: " + e.getMessage());
//     }
//     return ResponseEntity.badRequest().body("no file found in this document");

//     }

  
}