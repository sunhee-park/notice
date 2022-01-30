package com.rsupport.notice.web.dto;

import com.rsupport.notice.domain.posts.Files;
import com.rsupport.notice.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<FilesSaveRequestDto> filesDtoList;

    @Builder
    public PostsSaveRequestDto(String title
            , String content
            , String author
            , LocalDate startDate
            , LocalDate endDate
            , List<FilesSaveRequestDto> filesDtoList) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.startDate = startDate;
        this.endDate = endDate;
        this.filesDtoList = filesDtoList;
    }

    public PostsSaveRequestDto(HashMap<String ,String> paramMap) {
        LocalDate startDate = null;
        LocalDate endDate = null;
        if (paramMap.get("startDate") != null) {
            startDate =LocalDate.parse(paramMap.get("startDate"), DateTimeFormatter.ISO_DATE);
        }
        if (paramMap.get("endDate") != null) {
            endDate = LocalDate.parse(paramMap.get("endDate"), DateTimeFormatter.ISO_DATE);
        }

        this.title = paramMap.get("title");
        this.content = paramMap.get("content");
        this.author = paramMap.get("author");
        this.startDate = startDate;
        this.endDate = endDate;

    }


    public Posts toEntity() {

        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
