package com.rsupport.notice.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String origFileName;

    //@Column(nullable = false)
    private Long posts_Id;

    @Builder
    public Files(String filePath, String origFileName) {
        this.filePath = filePath;
        this.origFileName = origFileName;
    }
}
