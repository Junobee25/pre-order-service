package com.sideproject.preorderservice.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class ArticleLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id")
    private Article article;

    private ArticleLike(UserAccount userAccount, Article article) {
        this.userAccount = userAccount;
        this.article = article;
    }

}
