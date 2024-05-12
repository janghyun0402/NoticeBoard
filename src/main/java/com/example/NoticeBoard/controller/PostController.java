package com.example.NoticeBoard.controller;

import com.example.NoticeBoard.domain.Comment;
import com.example.NoticeBoard.domain.Post;
import com.example.NoticeBoard.domain.PostListDto;
import com.example.NoticeBoard.repository.CommentRepository;
import com.example.NoticeBoard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @GetMapping("/")        //게시글 리스트 출력
    public String getPosts(Model model) {
        List<Post> posts = postRepository.findAll();

        List<PostListDto> postsDtos = posts.stream()
                .map(post -> PostListDto.from(post))
                .toList();

        model.addAttribute("postDtos", postsDtos);

        return "index";
    }

    //좋아요 순 정렬
    @GetMapping("/like")
    public String getPostsByLike(Model model) {
        List<Post> posts = postRepository.findAll();

        List<PostListDto> postsDtos = posts.stream()
                .map(post -> PostListDto.from(post))
                .sorted(Comparator.comparingInt(PostListDto::getLikes).reversed())
                .toList();

        log.info(postsDtos.toString());
        model.addAttribute("postDtos", postsDtos);
        return "index";
    }

    //좋아요 역순 정렬
    @GetMapping("/dislike")
    public String getPostsByDislike(Model model) {
        List<Post> posts = postRepository.findAll();

        List<PostListDto> postsDtos = posts.stream()
                .map(post -> PostListDto.from(post))
                .sorted(Comparator.comparingInt(PostListDto::getLikes))
                .toList();

        log.info(postsDtos.toString());
        model.addAttribute("postDtos", postsDtos);
        return "index";
    }

    @GetMapping("/views")
    public String getPostsByViews(Model model) {
        List<Post> posts = postRepository.findAll();

        List<PostListDto> postsDtos = posts.stream()
                .map(post -> PostListDto.from(post))
                .sorted(Comparator.comparingInt(PostListDto::getViews).reversed())
                .toList();

        log.info(postsDtos.toString());
        model.addAttribute("postDtos", postsDtos);
        return "index";
    }

    @GetMapping("/posts")
    public String getPostPage() {
        return "posts";
    }

    @PostMapping("/posts")      //게시물 등록
    public String createPost(@RequestParam String title, @RequestParam String content) {
        Post newPost = Post.of(title, content, 0, 0);
        postRepository.save(newPost);

        return "redirect:/";
    }

    @GetMapping("/posts/{postId}")      //상세 페이지
    public String getPostDetail(@PathVariable Long postId, Model model) {
        Post post = postRepository.findById(postId);
        post.view();

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
        Post post = postRepository.findById(postId);

        model.addAttribute("post", post);
        return "post_edit";
    }

    @PostMapping("/posts/{postId}/edit")     //게시글 수정
    public String editPost(@PathVariable Long postId,
                           @RequestParam Long id,
                           @RequestParam String title,
                           @RequestParam String content,
                           @RequestParam Integer likes,
                           @RequestParam Integer views, Model model) {
        Post editedPost = Post.of(id, title, content, likes, views);
        postRepository.modify(editedPost);

        return "redirect:/posts/" + postId;
    }

    //게시글 추천
    @PostMapping("/posts/{postId}/like")
    public String like(@PathVariable Long postId){
        Post post = postRepository.findById(postId);
        post.like();
        postRepository.modify(post);

        return "redirect:/";
    }

    //게시글 비추천
    @PostMapping("/posts/{postId}/dislike")
    public String dislike(@PathVariable Long postId) {
        Post post = postRepository.findById(postId);
        post.dislike();
        postRepository.modify(post);

        return "redirect:/";
    }


}
