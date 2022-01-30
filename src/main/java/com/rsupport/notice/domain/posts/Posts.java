package com.rsupport.notice.domain.posts;

import com.rsupport.notice.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@DynamicInsert
@Entity
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    private LocalDate startDate;

    private LocalDate endDate;

    @ColumnDefault("0")
    private Long count;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="posts_id")
    private List<Files> filesList = new ArrayList<>();

    @Builder
    public Posts(String title, String content, String author, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void update(String title, String content, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
