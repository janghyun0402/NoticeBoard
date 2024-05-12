package com.example.NoticeBoard.controller;

import com.example.NoticeBoard.domain.Comment;
import com.example.NoticeBoard.domain.Post;
import com.example.NoticeBoard.domain.PostListDto;
import com.example.NoticeBoard.repository.CommentRepository;
import com.example.NoticeBoard.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    void getPostList() throws Exception {
        //given
        Model model = mock(Model.class);
        List<Post> mockPosts = new ArrayList<>();
        mockPosts.add(Post.of(1L, "title1", "body1", 5, 0));
        mockPosts.add(Post.of(2L, "title2", "body2", 10, 0));

        List<PostListDto> expectedPostDtos = mockPosts.stream()
                                .map(post -> PostListDto.from(post))
                                .toList();

        when(postRepository.findAll()).thenReturn(mockPosts);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("postDtos"));

        // 객체 필드의 값을 비교하고 싶음.
    }

    @Test
    void getPostDetail() throws Exception{
        //given
        Model model = mock(Model.class);
        Post post = Post.of(1L, "title", "content", 5, 0);
        List<Comment> comments = new ArrayList<>();
        comments.add(Comment.of("comment", post));

        when(postRepository.findById(1L)).thenReturn(post);
        when(commentRepository.findCommentsByPost(post)).thenReturn(comments);

        //when, then
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("post", post))
                .andExpect(model().attribute("comments", comments));
    }

    @Test
    void deletePost() {
    }

    @Test
    void getEditPost() {
    }

    @Test
    void editPost() {
    }
}