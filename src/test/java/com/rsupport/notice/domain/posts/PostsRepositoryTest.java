package com.rsupport.notice.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void save_Load() {
        String title = "테스트 게시글";
        String content = "테스트 본문";

        String filePath ="/file-upload";
        String origFileName ="test-upload.txt";

        Posts posts = Posts.builder()
                .title(title)
                .content(content)
                .author("prettysun.park@gmail.com")
                .build();

        Files files1 = Files.builder()
                .filePath(filePath)
                .origFileName(origFileName)
                .build();
        posts.getFilesList().add(files1);

        postsRepository.save(posts);

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

        List<Files> filesList = all.get(0).getFilesList();

        assertThat(filesList.size()).isEqualTo(1);
        assertThat(filesList.get(0).getPosts_Id()).isEqualTo(all.get(0).getId());
        assertThat(filesList.get(0).getOrigFileName()).isEqualTo(origFileName);
        assertThat(filesList.get(0).getFilePath()).isEqualTo(filePath);

    }
}
