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

    private Integer likes;

    private Integer views;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    //생성 메서드
    public static Post of(String title, String content, Integer likes, Integer views){
        return Post.builder()
                .title(title)
                .content(content)
                .likes(likes)
                .views(views)
                .build();
    }

    public static Post of(Long id, String title, String content, Integer likes, Integer views) {
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .likes(likes)
                .views(views)
                .build();
    }

    public void like() {
        this.likes++;
    }

    public void dislike() {
        this.likes--;
    }

    public void view(){
        this.views++;
    }

}
