package com.nizar.SahTech.mobileapp.chat;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList; // Import the ArrayList class
import java.time.LocalDateTime;
import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.doctor.repository.DoctorRepo;
import com.nizar.SahTech.mobileapp.chat.dto.ChatDTO;
import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;
import com.nizar.SahTech.util.IdGenerator;
import lombok.AllArgsConstructor;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final DoctorRepo doctorRepo;
    // private final IdGenerator idGenerator;

    public ResponseEntity<?> SendMessage(ChatDTO chatDTO, String connectedUser) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDateTime = currentDateTime.format(timeFormatter);

        if (chatDTO.getUserId() != null) {
            Optional<DoctorEntity> doctorOptional = doctorRepo.findByEmail(connectedUser);
            if (doctorOptional.isPresent()) {
                DoctorEntity doctor = doctorOptional.get();
                Optional<Chat> existingChat = chatRepository.findByDoctorIdAndUserId(doctor.getId(),
                chatDTO.getUserId());
                String msgg = doctor.getId() +"  :"+ chatDTO.getMessage() + "\n";
                byte[] msg = msgg.getBytes();
                Chat chat;
                if (existingChat.isPresent()) {
                    chat = existingChat.get();
                    byte[] existingData = chat.getMessage();
                    byte[] combinedData = concatenateByteArrays(existingData, msg);
                    chat.setMessage(combinedData);
                    chat.setLastmsg(formattedDateTime);
                } else {
                    chat = new Chat();
                    chat.setId(IdGenerator.generateId());
                    chat.setUserId(chatDTO.getUserId());
                    chat.setDoctorId(doctor.getId());
                    chat.setMessage(msg);
                    chat.setLastmsg(formattedDateTime);
                }
                chatRepository.save(chat);
                return ResponseEntity.ok("Message sent successfully");
            } else {
                return ResponseEntity.badRequest().body("Doctor not found");
            }
        } else {
            Optional<UserEntity> userOptional = userRepository.findByUsername(connectedUser);
            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                Optional<Chat> existingChat = chatRepository.findByDoctorIdAndUserId(chatDTO.getDoctorId(),
                        user.getId());
                String msgg = user.getId()+"  :" + chatDTO.getMessage() + "\n";
                byte[] msg = msgg.getBytes();
                Chat chat;
                if (existingChat.isPresent()) {
                    chat = existingChat.get();
                    byte[] existingData = chat.getMessage();
                    byte[] combinedData = concatenateByteArrays(existingData, msg);
                    chat.setMessage(combinedData);
                    chat.setLastmsg(formattedDateTime);

                } else {
                    chat = new Chat();
                    chat.setId(IdGenerator.generateId());
                    chat.setUserId(user.getId());
                    chat.setDoctorId(chatDTO.getDoctorId());
                    chat.setMessage(msg);
                    chat.setLastmsg(formattedDateTime);

                }
                chatRepository.save(chat);
                return ResponseEntity.ok("Message sent successfully");
            } else {
                return ResponseEntity.badRequest().body("User not found");
            }
        }
    }

    public ResponseEntity<?> GetMessage(String connectedUser, ChatDTO chatDTO) {
        Optional<Chat> chatOptional = chatRepository.findById(chatDTO.getConversationId());
        try {
            if (chatOptional.isPresent()) {
                Chat chat = chatOptional.get();
                byte[] message = chat.getMessage();
                String msg = new String(message);
                return ResponseEntity.ok(msg);
            } else {
                return ResponseEntity.badRequest().body("No messages found");
            }
        } catch (Exception e) {
        }
        return null;  

    }

    // concatenate two byte arrays
    private byte[] concatenateByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public List<ChatDTO> GetAllMessage(String connectedUser, String role) {

        List<ChatDTO> targetList = new ArrayList<>();
   try {
    

        if(role.equals("doctor"))
        {
            Optional<DoctorEntity> doctorOptional = doctorRepo.findByEmail(connectedUser);
            List<Chat> chatOptional = chatRepository.findByDoctorId(doctorOptional.get().getId());
            for (Chat chat : chatOptional) {
                Optional<UserEntity> userOptional = userRepository.findByUsername(chat.getUserId());
                ChatDTO chatDTO = new ChatDTO();
                byte[] message = chat.getMessage();
                String conversation = new String(message);
                // chatDTO.setMessage(conversation);
                chatDTO.setUserName(userOptional.get().getUsername());
                chatDTO.setImage(userOptional.get().getImage());
                chatDTO.setMessage(GetLastLine(conversation));
                chatDTO.setLastmsg(chat.getLastmsg());
                targetList.add(chatDTO);

            }
            
            return  targetList;


            
            
        }
        else if(role.equals("user"))
        {
            Optional<UserEntity> userOptional = userRepository.findByUsername(connectedUser);
            // Optional<Chat> chatOptional = chatRepository.findByUserId(userOptional.get().getId());
            List<Chat> chatList = chatRepository.findByUserId(userOptional.get().getId());
            for (Chat chat : chatList) {

                Optional<DoctorEntity> doctorOptional = doctorRepo.findById(chat.getDoctorId());
                ChatDTO chatDTO = new ChatDTO();
                byte[] message = chat.getMessage();
                String conversation = new String(message);
                // chatDTO.setMessage(conversation);
                chatDTO.setDoctorName(doctorOptional.get().getUsername());
                chatDTO.setImage(doctorOptional.get().getImage());
                chatDTO.setMessage(GetLastLine(conversation));
                chatDTO.setLastmsg(chat.getLastmsg());
                System.out.println(chatDTO.getLastmsg());
                targetList.add(chatDTO);

            }
            return  targetList;

        }
        return null;
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage()   );
        return null;
    }
    
      
    }

    public  static String  GetLastLine(String conversation){
        String[] lines = conversation.split("\n");
        String lastLine = lines[lines.length - 1];
        return lastLine;


    }

}
