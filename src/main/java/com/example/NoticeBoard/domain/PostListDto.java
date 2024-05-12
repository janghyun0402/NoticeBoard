package com.example.NoticeBoard.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Builder
@Getter
public class PostListDto {

    private Long id;
    private String title;
    private Integer likes;
    private Integer views;

    public static PostListDto from(Post post) {
        return PostListDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .likes(post.getLikes())
                .views(post.getViews())
                .build();
    }
}
