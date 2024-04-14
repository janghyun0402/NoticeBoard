package com.example.NoticeBoard.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Column(length = 2000)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    //생성 메서드
    public static Post of(String title, String content){
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }

    public static Post of(Long id, String title, String content) {
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
    }

}
