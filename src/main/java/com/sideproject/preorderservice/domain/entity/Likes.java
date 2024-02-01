package com.sideproject.preorderservice.domain.entity;

import com.sideproject.preorderservice.domain.constant.LikeType;
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
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    @Enumerated(EnumType.STRING)
    private LikeType likeType;

    private Long targetId;

    private Boolean deleted;

    public Likes() {

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
