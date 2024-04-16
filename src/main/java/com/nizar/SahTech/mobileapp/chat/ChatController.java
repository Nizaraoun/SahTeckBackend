package com.nizar.SahTech.mobileapp.chat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.nizar.SahTech.mobileapp.chat.dto.ChatDTO;

import java.security.Principal;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/Send_Message")
    public ResponseEntity<?> SendMessage(@RequestBody ChatDTO chatDTO , Principal princepal ) {
        String connectedUser = princepal.getName();
        System.out.println(connectedUser);
     return   chatService.SendMessage(chatDTO  , connectedUser);
    }
@GetMapping("/Get_Message")
public ResponseEntity<?> GetMessage(Principal principal ,@RequestBody ChatDTO chatDTO) {
    if (chatDTO.getConversationId() == null){
        return ResponseEntity.badRequest().body("invalid conversation id");
    }
    else {
        String connectedUser = principal.getName();
        return chatService.GetMessage(connectedUser ,chatDTO);}
    }
    @GetMapping("/Get_All_Message")
    public List<ChatDTO> GetAllMessage(Principal principal ,@RequestParam("role")String role ) {
        String connectedUser = principal.getName();
       
            return chatService.GetAllMessage(connectedUser,role );
        
    
    
}


    
}
