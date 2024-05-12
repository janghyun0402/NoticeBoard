package com.example.NoticeBoard.repository;

import com.example.NoticeBoard.domain.Post;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(PostRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void save() {
        // given
        Post post = Post.of("title", "contents", 0, 0);

        // when
        postRepository.save(post);

        // then
        Post findPost = em.find(Post.class, post.getId());
        assertThat(findPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(findPost.getContent()).isEqualTo(post.getContent());

    }

    @Test
    void findAll() {
        // given
        Post post1 = Post.of("title1", "asdf", 0, 0);
        Post post2 = Post.of("title2", "sdf", 0, 0);
        em.persist(post1);
        em.persist(post2);

        // when
        List<Post> all = postRepository.findAll();

        // then
        assertThat(2).isEqualTo(all.size());
    }

    @Test
    void deleteById() {

    }

    @Test
    void modify() {
        //given
        Post post = Post.of("title", "content", 0, 0);
        em.persist(post);

        //when
        Post modified = Post.of(post.getId(), "modified", "modified", 0, 0);
        postRepository.modify(modified);
        Post findPost = em.find(Post.class, post.getId());

        //then
        assertThat(findPost.getTitle()).isEqualTo("modified");
        assertThat(findPost.getContent()).isEqualTo("modified");
    }
}