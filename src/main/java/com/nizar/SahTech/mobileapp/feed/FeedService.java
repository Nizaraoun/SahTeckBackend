package com.nizar.SahTech.mobileapp.feed;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.doctor.repository.DoctorRepo;
import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;
import com.nizar.SahTech.users.following_list_doctor.followingRepository;
import com.nizar.SahTech.users.following_list_doctor.followinglist;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
@Service
@AllArgsConstructor
public class FeedService {
    
    private final FeedRepository feedRepository;
    private final followingRepository followingRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final DoctorRepo DoctorRepository;




    //add post
    public ResponseEntity<?> addPost(FeedDTO post) {
        LocalDateTime createdAt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedCreatedAt = createdAt.format(formatter);
        // Optional<Feed> feeds = feedRepository.findById(post.getPostId());
        Feed feed = new Feed();

        try {
            feed.setUserId(post.getUserId());
            feed.setContent(post.getContent());
            feed.setCommentcount(0L);
            feed.setUserRole(post.getRole());
            feed.setAnonymous(post.isAnonymous());
            feed.setCreatedAt(formattedCreatedAt);
            feedRepository.save(feed);
            return ResponseEntity.ok("Post added successfully"); 
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add post: " + e.getMessage());
        }
    }
    //delete post
    public ResponseEntity<?> deletePost(Long id) {
        Optional<Feed> feed = feedRepository.findById(id);
        try {
            if (feed.isPresent()) {
                feedRepository.delete(feed.get());
                return ResponseEntity.ok("Post deleted successfully");
            }
            return ResponseEntity.ok("Post not found");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete the post: " + e.getMessage());
        }

    }
            // get all User posts
    public ResponseEntity<List<FeedDTO>> getAllPosts(String role ,String connection) {
        List<FeedDTO> feeddto = new ArrayList<>();

        try {
            if (role.equals("user") ) {
                
                List<Feed> feeds = feedRepository.findAll();
               
                    for (Feed feed : feeds) {
                        FeedDTO feedDto = new FeedDTO();

                        if (feed.isAnonymous()) {
                            feedDto.setPostId(feed.getPostId());
                            feedDto.setContent(feed.getContent());
                            feedDto.setCommentCount(feed.getCommentcount());
                            feedDto.setCreatedAt(feed.getCreatedAt());
                            feeddto.add(feedDto);

                        } else {
                        Optional<UserEntity> user = userRepository.findById(feed.getUserId());
                        if (user.isPresent()) {
                            feedDto.setPostId(feed.getPostId());
                            feedDto.setContent(feed.getContent());
                            feedDto.setCommentCount(feed.getCommentcount());
                            feedDto.setCreatedAt(feed.getCreatedAt());
                            feedDto.setSenderImg(user.get().getImage());
                            feedDto.setSenderName(user.get().getEmail());
                            feeddto.add(feedDto);
                        
                        }
                        }
                }
                return ResponseEntity.ok(feeddto);

            }
            else
            {
                Optional<UserEntity> user = userRepository.findByUsername(connection);
                Optional<followinglist> following = followingRepository.findById(user.get().getId());
                if (following.isPresent()) {
                    byte[] doctorIdBytes = following.get().getDoctorId();
                    String doctorIdString = new String(doctorIdBytes);
                    String[] doctorIdLines = doctorIdString.split("\n");
                    for (String line : doctorIdLines) {
                        List<Feed> feed = feedRepository.findAllByUserId(line);
                        if (feed.size() > 0){
                            Optional<DoctorEntity> doctor = DoctorRepository.findById(line);
                        for(Feed feed1 : feed){
                            FeedDTO feeddto1 = new FeedDTO();
                            feeddto1.setPostId(feed1.getPostId());
                            feeddto1.setContent(feed1.getContent());
                            feeddto1.setCommentCount(feed1.getCommentcount());
                            feeddto1.setCreatedAt(feed1.getCreatedAt());
                            feeddto1.setSenderImg(doctor.get().getImage());
                            feeddto1.setSenderName(doctor.get().getEmail());
                            feeddto.add(feeddto1);
                        }
                        
                        } 
                    }
                }
            }
                } catch (Exception e) {
        }
        return ResponseEntity.ok(feeddto);
    }

    //  ---------------------------------------------------------------------------------------------------------//
                                                        // add comment//
    // -----------------------------------------------------------------------------------------------------------//

    
    // add comment
    public ResponseEntity<?> addComment(FeedDTO comment) {
        LocalDateTime createdAt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedCreatedAt = createdAt.format(formatter);
        Optional<Feed> feed = feedRepository.findById(comment.getPostId());
        
        try {
            if (feed.isPresent()) {
                Feed feed1 = feed.get();
                feed1.setCommentcount(feed1.getCommentcount() + 1);
                Comment comment1 = new Comment();
                comment1.setPostId(comment.getPostId());
                comment1.setUserId(comment.getUserId());
                comment1.setContent(comment.getContent());
                comment1.setCreatedAt(formattedCreatedAt);
                commentRepository.save(comment1);
                feedRepository.save(feed1);
                return ResponseEntity.ok("Comment added successfully");
            }
            return ResponseEntity.badRequest().body("Post not found");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add comment: " + e.getMessage());
        }
    }
            // Get all comments
    public ResponseEntity<List<FeedDTO>>  getAllComments(Long postId) {
        List<FeedDTO> feeddto = new ArrayList<>();

        try {
            Optional<Feed> feed = feedRepository.findById(postId);
            System.out.println(feed);
            if (feed.isPresent()) {
                List<Comment> comments = commentRepository.findAllByPostId(postId);
                for (Comment comment : comments) {
                    Optional<DoctorEntity> doctor = DoctorRepository.findById(comment.getUserId());
                    FeedDTO feedDto = new FeedDTO();
                    feedDto.setPostId(comment.getPostId());
                    feedDto.setSenderName(doctor.get().getEmail());
                    feedDto.setSenderImg(doctor.get().getImage());
                    feedDto.setContent(comment.getContent());
                    feedDto.setCreatedAt(comment.getCreatedAt());
                    feeddto.add(feedDto);
                }
            }
            return ResponseEntity.ok(feeddto);

        } catch (Exception e) {

            
        }
        return ResponseEntity.ok(feeddto);
        
    }
}
