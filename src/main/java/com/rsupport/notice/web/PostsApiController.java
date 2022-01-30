package com.rsupport.notice.web;

import com.rsupport.notice.service.posts.FilesService;
import com.rsupport.notice.service.posts.PostsService;
import com.rsupport.notice.web.dto.FilesSaveRequestDto;
import com.rsupport.notice.web.dto.PostsResponseDto;
import com.rsupport.notice.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    private final FilesService fileService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestParam HashMap<String ,String> paramMap,
                     @RequestParam(value = "files", required = false) MultipartFile[] files) {
        List<FilesSaveRequestDto> filesDtoList = fileService.save(files);

        return postsService.save(new PostsSaveRequestDto(paramMap), filesDtoList);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id,
                       @RequestParam HashMap<String ,String> paramMap,
                       @RequestParam(value = "attaches", required = false) List<String> attaches,
                       @RequestParam(value = "files", required = false) MultipartFile[] files) {
        List<FilesSaveRequestDto> filesDtoList = fileService.save(files);
        return postsService.update(id, new PostsSaveRequestDto(paramMap), filesDtoList, attaches);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id) {
        postsService.increaseViewCount(id);
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public void deleteById (@PathVariable Long id) {
        postsService.deleteById(id);
    }
}
