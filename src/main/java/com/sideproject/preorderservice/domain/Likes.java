package com.sideproject.preorderservice.domain;

import com.sideproject.preorderservice.configuration.LikeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinTable(name = "userNumber")
    private UserAccount userAccount;

    @Enumerated(EnumType.STRING)
    private LikeType likeType;

    private Long targetId;

    private Boolean deleted;

    protected Likes() {

    }

    private Likes(UserAccount userAccount, LikeType likeType, Long targetId, Boolean deleted) {
        this.userAccount = userAccount;
        this.likeType = likeType;
        this.targetId = targetId;
        this.deleted = deleted;
    }

    public static Likes of(UserAccount userAccount, LikeType likeType, Long targetId) {
        return new Likes(userAccount, likeType, targetId, false);
    }
    public void toggleDeleted() {
        this.deleted = !this.deleted;
    }
}
