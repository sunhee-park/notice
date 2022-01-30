package com.rsupport.notice.web.dto;

import com.rsupport.notice.domain.posts.Files;
import com.rsupport.notice.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdDate;
    private Long count;
    private List<FilesResponseDto> files = new ArrayList<>();

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.createdDate = entity.getCreatedDate();
        this.count = entity.getCount();
        for (Files file :entity.getFilesList()) {
            files.add(FilesResponseDto
                    .builder()
                    .id(String.valueOf(file.getId()))
                    .fileName(file.getOrigFileName())
                    .build());
        }

    }


}
