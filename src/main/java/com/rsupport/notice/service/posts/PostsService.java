package com.rsupport.notice.service.posts;

import com.rsupport.notice.domain.posts.Files;
import com.rsupport.notice.domain.posts.Posts;
import com.rsupport.notice.domain.posts.PostsRepository;
import com.rsupport.notice.web.dto.FilesSaveRequestDto;
import com.rsupport.notice.web.dto.PostsResponseDto;
import com.rsupport.notice.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto postsDto, List<FilesSaveRequestDto> filesDtoList) {
        Posts posts = postsDto.toEntity();
        for (FilesSaveRequestDto filesDto : filesDtoList) {
            posts.getFilesList().add(filesDto.toEntity());
        }
        return postsRepository.save(posts).getId();
    }

    @Transactional
    public Long update(Long id,
                       PostsSaveRequestDto requestDto,
                       List<FilesSaveRequestDto> filesDtoList,
                       List<String> attaches) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.getFilesList().removeIf(files ->attaches == null || !attaches.contains(String.valueOf(files.getId())));

        for (FilesSaveRequestDto filesDto : filesDtoList) {
            posts.getFilesList().add(filesDto.toEntity());
        }

        posts.update(requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getStartDate(),
                requestDto.getEndDate());

        return id;
    }

    @Transactional
    public void deleteById(Long id) {
        postsRepository.deleteById(id);
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional
    public int increaseViewCount(Long id) {
        return postsRepository.increaseViewCount(id);
    }

}
