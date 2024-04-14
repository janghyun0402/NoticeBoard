package com.example.NoticeBoard.controller;

import com.example.NoticeBoard.domain.Comment;
import com.example.NoticeBoard.domain.Post;
import com.example.NoticeBoard.domain.PostListDto;
import com.example.NoticeBoard.repository.CommentRepository;
import com.example.NoticeBoard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @GetMapping("/")        //게시글 제목 리스트 출력
    public String getPostList(Model model){
        List<Post> postList = postRepository.findAll();

        List<PostListDto> postListDtos = postList.stream()
                .map(post -> PostListDto.from(post))
                .toList();

        model.addAttribute("postList", postListDtos);

        return "index";
    }

    @GetMapping("/posts")
    public String getPostPage() {
        return "posts";
    }

    @PostMapping("/posts")      //게시물 등록
    public String createPost(@RequestParam String title, @RequestParam String content){
        Post newPost = Post.of(title, content);
        postRepository.save(newPost);

        return "redirect:/";
    }

    @GetMapping("/posts/{postId}")      //상세 페이지
    public String getPostDetail(@PathVariable Long postId, Model model){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException());

        List<Comment> comments = commentRepository.findCommentsByPost(post);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "post_detail";
    }


    @PostMapping("/posts/{postId}/delete")      //게시글 삭제
    public String deletePost(@PathVariable Long postId) {
        postRepository.deleteById(postId);

        return "redirect:/";
    }

    @GetMapping("/posts/{postId}/edit")       //수정 페이지
    public String getEditPost(@PathVariable Long postId, Model model) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException());

        model.addAttribute("post", post);
        return "post_edit";
    }

    @PostMapping("/posts/{postId}/edit")     //게시글 수정
    public String editPost(@PathVariable Long postId,
                           @RequestParam Long id,
                           @RequestParam String title,
                           @RequestParam String content, Model model) {
        Post editedPost = Post.of(id, title, content);
        postRepository.save(editedPost);

        return "redirect:/posts/" + postId;
    }


}
