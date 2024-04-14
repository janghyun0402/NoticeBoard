package com.example.NoticeBoard.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public static Comment of(String content, Post post) {
        return Comment.builder()
                .content(content)
                .post(post)
                .build();
    }
}
