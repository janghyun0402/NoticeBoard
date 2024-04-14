package com.example.NoticeBoard.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class PostListDto {

    private Long id;
    private String title;

    public static PostListDto from(Post post) {
        return PostListDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .build();
    }
}
