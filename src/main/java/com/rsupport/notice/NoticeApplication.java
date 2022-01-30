package com.rsupport.notice;

import com.rsupport.notice.common.FileProperties;
import com.rsupport.notice.service.posts.FilesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties(FileProperties.class)
@EnableJpaAuditing
@SpringBootApplication
public class NoticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoticeApplication.class, args);
    }

    @Bean
    CommandLineRunner init(FilesService storageService) {
        return (args) -> {
            storageService.init();
        };
    }
}
