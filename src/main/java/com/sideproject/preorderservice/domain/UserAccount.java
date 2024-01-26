package com.sideproject.preorderservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString(callSuper = true )
@Table(indexes = {

})

@Entity
public class UserAccount extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 50)
    private String userName;
    @Setter
    @Column(nullable = false)
    private String userPassword;
    @Setter
    @Column(nullable = false, length = 50)
    private String email;
    @Setter
    @Column
    private Boolean emailVerified;
    @Setter
    @Column(nullable = false)
    private String memo;
    @Lob
    @Column(nullable = false)
    private byte[] profilePicture;

    protected UserAccount() {

    }

    private UserAccount(String userName, String userPassword, String email, Boolean emailVerified, String memo, byte[] profilePicture) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.email = email;
        this.emailVerified = emailVerified;
        this.memo = memo;
        this.profilePicture = profilePicture;
    }

    public static UserAccount of(String userName, String userPassword, String email, Boolean emailVerified, String memo, byte[] profilePicture) {
        return new UserAccount(userName, userPassword, email, emailVerified, memo, profilePicture);
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
