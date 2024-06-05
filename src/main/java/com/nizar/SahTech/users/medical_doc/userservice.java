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
import com.nizar.SahTech.util.IdGenerator;

import java.util.List;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.UrlResource;

import com.nizar.SahTech.doctor.repository.DoctorRepo;
import com.twilio.rest.chat.v1.service.User;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.core.io.Resource;

@Service
@RequiredArgsConstructor
public class userservice {
    private final UserRepository userRepository; // Assuming you have a repository for users
private final FilesRepository docFileRepository; // Assuming you have a repository for medical files
private final DocRepository docRepository; // Assuming you have a repository for documents
private final String rootDir = "uploads/user/";
   
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
  public ResponseEntity<String> saveMedicalFileForUser(Principal connectedUser, String id, MultipartFile file) {
    try {
        // Check if user exists
        Optional<UserEntity> userOptional = userRepository.findByUsername(connectedUser.getName());
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        UserEntity user = userOptional.get();

        // Check if document exists
        Optional<Document> documentOptional = docRepository.findById(id);
        if (!documentOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Document not found");
        }
        Document document = documentOptional.get();

        // Check if medical file exists
        Optional<MedicalFile> filesOptional = docFileRepository.findById(id);
        if (!filesOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Medical file not found");
        }
        MedicalFile oldFile = filesOptional.get();

        // Define upload directory and file path
        String uploadDir = rootDir + user.getId() + "/" + document.getDescription() + "/";
        Path copyLocation = Paths.get(uploadDir + file.getOriginalFilename());

        // Ensure directory exists
        Files.createDirectories(copyLocation.getParent());

        // Copy file to target location
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

        // Update file data in the medical file
        byte[] newFileData = (user.getId() + "/" + document.getDescription() + "/" + file.getOriginalFilename()+" ").getBytes();
        byte[] updatedFileData = concatenateByteArrays(oldFile.getFile(), newFileData);

        oldFile.setFile(updatedFileData);
        document.setNumber(document.getNumber() + 1);

        // Save updated document and file
        docRepository.save(document);
        docFileRepository.save(oldFile);

        return ResponseEntity.ok(oldFile.getFile().toString());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save file: " + e.getMessage());
    }
}

// Helper method to concatenate two byte arrays
private byte[] concatenateByteArrays(byte[] array1, byte[] array2) {
    byte[] result = new byte[array1.length + array2.length];
    System.arraycopy(array1, 0, result, 0, array1.length);
    System.arraycopy(array2, 0, result, array1.length, array2.length);
    return result;
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
    if (documentDTO.getDescription() != null) {
            Optional<Document> document = docRepository.findByUserIdAndDescription(user.get().getId(), documentDTO.getDescription());
    Document doc = new Document();
    MedicalFile file = new MedicalFile();
    byte[] bytes = "".getBytes();
 
    try {
        if (!document.isPresent()) {
            doc.setDescription(documentDTO.getDescription());
            doc.setNumber(0);
            doc.setId( IdGenerator.generateId(24));
            doc.setDocCode(IdGenerator.generateId(5));
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
}
    return ResponseEntity.badRequest().body("Document already exists for user with ID: " + user.get().getId());
}
// this method is used to delet the medical file for the user
public ResponseEntity<?> deleteMedicalFileForUser(Principal connecteduser, String docId) {
    Optional<UserEntity> user = userRepository.findByUsername(connecteduser.getName());
    Optional<MedicalFile> files = docFileRepository.findById(docId);
    Optional<Document> document = docRepository.findById(docId);
    try {
        if (files.isPresent()) {
            docFileRepository.delete(files.get());
            docRepository.delete(document.get());
            return ResponseEntity.ok("Medical File deleted successfully for user with ID: " + user.get().getId());
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete Medical File: " + e.getMessage());
    }
    return ResponseEntity.badRequest().body("Medical File not found for user with ID: " + user.get().getId());}



// Helper method to concatenate two byte arrays

public ResponseEntity <?> getMedicalFileForUser(fileDTO filedto ,Principal connecteduser) {
    MedicalFileDTO filedoc = new MedicalFileDTO();


if (filedto.getRole().equals("user")) {

    Optional<MedicalFile> files = docFileRepository.findById(filedto.getId());
    try {
        if (files != null) {
            byte[] file = files.get().getFile();
                String newFile = new String(file);
            filedoc.setFile(newFile);

            return ResponseEntity.ok(filedoc);
            
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get Medical File: " + e.getMessage());
    }
}    

else{
    Optional<Document> document = docRepository.findByUserIdAndDocCode( filedto.getUserId(),filedto.getSecretKey());
    try {
        Optional<MedicalFile> file = docFileRepository.findById(document.get().getId());
        if (file != null) {
            byte[] files = file.get().getFile();
            String newFile = new String(files);
            filedoc.setFile(newFile);
            filedoc.setName(document.get().getDescription());
            // Generate a new document code
            document.get().setDocCode(IdGenerator.generateId(5));
            docRepository.save(document.get());
            return ResponseEntity.ok(filedoc);
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get Medical File: " + e.getMessage());}
}
return ResponseEntity.badRequest().body("User not found");
        
  

}

  
}