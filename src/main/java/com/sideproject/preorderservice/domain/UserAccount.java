package com.sideproject.preorderservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(indexes = {

})

@Entity
public class UserAccount extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 50)
    private String email;
    @Setter
    @Column(nullable = false, length = 50)
    private String userName;
    @Setter
    @Column(nullable = false)
    private String userPassword;
    @Setter
    @Column
    private Boolean emailVerified;
    @Setter
    @Column(nullable = false)
    private String memo;
    @Setter
    @Column(nullable = false, length = 500)
    private String profilePicture;

    protected UserAccount() {

    }

    private UserAccount(String email, String userName, String userPassword, Boolean emailVerified, String memo, String profilePicture) {
        this.email = email;
        this.userName = userName;
        this.userPassword = userPassword;
        this.emailVerified = emailVerified;
        this.memo = memo;
        this.profilePicture = profilePicture;
    }

    public static UserAccount of(String email, String userName, String userPassword, Boolean emailVerified, String memo, String profilePicture) {
        return new UserAccount(email, userName, userPassword, emailVerified, memo, profilePicture);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
