package com.nizar.SahTech.mobileapp.feed;
import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap.Option;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.doctor.repository.DoctorRepo;
import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;


import lombok.AllArgsConstructor;
import java.security.Principal;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class FeedController {
    private final FeedService feedService;
    private final DoctorRepo doctorRepository;
    private final UserRepository UserRep;


// -----------------------------------------
               // add post
// -----------------------------------------
    @PostMapping("/add-post")
    public ResponseEntity<?> addPost(@RequestBody FeedDTO post){
        return feedService.addPost(post);
    }
// -----------------------------------------
               // delete post
// -----------------------------------------

    @DeleteMapping("/delete-post")
    public ResponseEntity<?> deletePost(@RequestParam Long id) {
        return feedService.deletePost(id);
    }
// -----------------------------------------
               // get all posts
// -----------------------------------------

    @GetMapping("/get-all-posts")
    public ResponseEntity<List<FeedDTO>> getAllPosts(@RequestParam String role ,Principal principal ) {
        String user = principal.getName(); 
            Optional<UserEntity> User = UserRep.findByUsername(user);
            return feedService.getAllPosts(role, User.get().getId());}
    
// -------------------------------------------------
                // add  comments
// -------------------------------------------------
    @PostMapping("/add-comment")
    public ResponseEntity<?> addComment(@RequestBody FeedDTO comment) {
        return feedService.addComment(comment);
    }   
// -----------------------------------------
               // get all comments
// -----------------------------------------
     @GetMapping("/get-all-comments")
    public ResponseEntity<List<FeedDTO>> getAllComments(@RequestParam Long postId) {
        return feedService.getAllComments(postId);}
    
}
