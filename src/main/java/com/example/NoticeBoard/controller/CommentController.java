package com.example.NoticeBoard.controller;

import com.example.NoticeBoard.domain.Comment;
import com.example.NoticeBoard.domain.Post;
import com.example.NoticeBoard.repository.CommentRepository;
import com.example.NoticeBoard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @PostMapping("/posts/{postId}/comments")        //댓글 등록
    public String postComment(@PathVariable Long postId,
                              @RequestParam String content) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException());
        Comment comment = Comment.of(content, post);
        commentRepository.save(comment);

        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{postId}/comments/delete")     //댓글 삭제
    public String deleteComment(@PathVariable Long postId, @RequestParam Long commentId) {
        commentRepository.deleteById(commentId);

        return "redirect:/posts/" + postId;
    }
}
