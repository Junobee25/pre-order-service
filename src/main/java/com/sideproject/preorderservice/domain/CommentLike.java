package com.sideproject.preorderservice.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "article_comment_id")
    private ArticleComment articleComment;

    private CommentLike(UserAccount userAccount, ArticleComment articleComment) {
        this.userAccount = userAccount;
        this.articleComment = articleComment;
    }
}
