package com.rsupport.notice.web.dto;

import com.rsupport.notice.domain.posts.Files;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FilesSaveRequestDto {

    private String fileName;
    private String filePath;
    private String origFileName;

    @Builder
    public FilesSaveRequestDto(String fileName, String filePath, String origFileName) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.origFileName = origFileName;

    }

    public Files toEntity() {
        return Files.builder()
                .filePath(filePath)
                .origFileName(origFileName)
                .build();
    }

}
