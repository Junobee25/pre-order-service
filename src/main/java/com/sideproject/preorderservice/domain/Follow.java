package com.sideproject.preorderservice.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "from_user_id", nullable = false)
    private UserAccount fromUser;

    @ManyToOne(optional = false)
    @JoinColumn(name = "to_user_id", nullable = false)
    private UserAccount toUser;

    private Follow(UserAccount fromUser, UserAccount toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
