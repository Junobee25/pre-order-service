package com.sideproject.preorderservice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
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

    public static Follow of(UserAccount fromUser, UserAccount toUser){
        Follow follow = new Follow();
        follow.setFromUser(fromUser);
        follow.setToUser(toUser);
        return follow;
    }
}
