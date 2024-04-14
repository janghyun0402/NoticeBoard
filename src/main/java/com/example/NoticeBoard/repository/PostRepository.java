package com.example.NoticeBoard.repository;

import com.example.NoticeBoard.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
