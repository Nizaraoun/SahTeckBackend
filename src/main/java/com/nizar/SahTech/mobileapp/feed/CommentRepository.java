package com.nizar.SahTech.mobileapp.feed;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// repository for comment
public interface CommentRepository  extends JpaRepository<Comment, Long>{

    List<Comment> findAllByPostId(Long postId);


} 