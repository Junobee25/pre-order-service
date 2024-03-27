package com.sideproject.preorderservice.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
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
@Data
public class UserAccount implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 100)
    private String email;
    @Setter
    @Column(nullable = false, length = 50)
    private String userName;
    @Setter
    @Column(nullable = false)
    private String userPassword;
    @Column
    private Boolean emailVerified;
    @Setter
    @Column(nullable = false, length = 500)
    private String memo;

    protected UserAccount() {

    }

    private UserAccount(String email, String userName, String userPassword, Boolean emailVerified, String memo) {
        this.email = email;
        this.userName = userName;
        this.userPassword = userPassword;
        this.emailVerified = emailVerified;
        this.memo = memo;
    }

    public static UserAccount of(String email, String userName, String userPassword, Boolean emailVerified, String memo) {
        return new UserAccount(email, userName, userPassword, emailVerified, memo);
    }

    public void emailVerifiedSuccess() {
        this.emailVerified = true;
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
