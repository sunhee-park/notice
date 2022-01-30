package com.rsupport.notice.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FilesResponseDto {
    private String id;
    private String fileName;

    @Builder
    public FilesResponseDto(String id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }
}
