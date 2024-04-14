package com.example.NoticeBoard.repository;

import com.example.NoticeBoard.domain.Comment;
import com.example.NoticeBoard.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByPost(Post post);
}
