package com.diving_fish.ebook.entity;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usercontext")
public class UserEntity implements UserDetails {
    @Id
    private Integer id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private Integer role; // 0 : admin,  1 : user
    @Column
    private Boolean enabled;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private List<UserEntity> users;

    public UserEntity() {

    }
    public void set(Integer _id, String _username, String _password, String _email, Integer _role) {
        id = _id;
        username = _username;
        password = _password;
        email = _email;
        role = _role;
        enabled = true;
    }
    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Integer getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        List<UserEntity> users = this.users;
        for (UserEntity user : users) {
            auths.add(new SimpleGrantedAuthority( (user.getRole() == 0) ? "ADMIN": "USER"));
        }
        return auths;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
