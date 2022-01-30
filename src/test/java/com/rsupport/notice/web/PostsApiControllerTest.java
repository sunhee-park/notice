package com.rsupport.notice.web;

import java.io.File;
import java.util.List;

import com.rsupport.notice.domain.posts.Files;
import com.rsupport.notice.domain.posts.FilesRepository;
import com.rsupport.notice.domain.posts.Posts;
import com.rsupport.notice.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private FilesRepository filesRepository;

    @After
    public void tearDown() {
        filesRepository.deleteAll();
        postsRepository.deleteAll();
    }

    @Test
    public void save() {
        String title = "title";
        String content = "content";
        String fileName = "test-upload.txt";

        ClassPathResource resource = new ClassPathResource(fileName, getClass());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("files", resource);
        body.add("files", resource);
        body.add("files", resource);
        body.add("title", title);
        body.add("content", content);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        String serverUrl = "http://localhost:" + port + "/api/v1/posts";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(serverUrl, requestEntity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

        List<Files> filesList = all.get(0).getFilesList();
        assertThat(filesList.size()).isEqualTo(3);
        assertThat(filesList.get(0).getOrigFileName()).isEqualTo(fileName);
        assertThat(filesList.get(0).getPosts_Id()).isEqualTo(all.get(0).getId());

        for (Files files : filesList) {
            File file = new File(files.getFilePath());
            assertThat(file.exists()).isTrue();
            assertThat(file.length()).isGreaterThan(0L);
        }
    }

    @Test
    public void update() {
        String fileName = "test-upload.txt";

        Posts beforePosts = Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build();
        Files beforeFiles = Files.builder()
                .filePath("/test/test.txt")
                .origFileName("test.txt")
                .build();
        beforePosts.getFilesList().add(beforeFiles);

        Posts savedPosts = postsRepository.save(beforePosts);

        String expectedTitle = "updatedTitle";
        String expectedContent = "updatedContents";

        ClassPathResource resource = new ClassPathResource(fileName, getClass());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("files", resource);
        body.add("title", expectedTitle);
        body.add("content", expectedContent);
        body.add("attaches", savedPosts.getFilesList().get(0).getId());

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        String serverUrl = "http://localhost:" + port + "/api/v1/posts/" + savedPosts.getId();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(serverUrl, HttpMethod.PUT, requestEntity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

        List<Files> filesList = all.get(0).getFilesList();
        assertThat(filesList.size()).isEqualTo(2);

    }

    @Test
    public void findById() {

        String title = "title";
        String fileName ="test.txt";
        String fileName2 ="test2.txt";

        Posts posts = Posts.builder()
                .title(title)
                .content("content")
                .author("author")
                .build();
        Files files = Files.builder()
                .filePath("/test/test.txt")
                .origFileName(fileName)
                .build();

        posts.getFilesList().add(files);

        Posts savedPosts = postsRepository.save(posts);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>( headers);

        String serverUrl = "http://localhost:" + port + "/api/v1/posts/" + savedPosts.getId();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(serverUrl, HttpMethod.GET, requestEntity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains(title);
        assertThat(responseEntity.getBody()).contains(fileName);

    }
}
