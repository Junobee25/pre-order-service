package com.sideproject.preorderservice.domain;

import com.sideproject.preorderservice.dto.UserAccountDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(indexes = {

})

@Entity
public class UserAccount implements UserDetails {
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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

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

    public static UserAccount from(String email, String userName, String userPassword, Boolean emailVerified, String memo, String profilePicture) {
        return new UserAccount(email, userName, userPassword, emailVerified, memo, profilePicture);
    }

    public void emailVerifiedSuccess() {
        this.emailVerified = true;
    }

    @PrePersist
    public void setCreationTimestamp() {
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
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
