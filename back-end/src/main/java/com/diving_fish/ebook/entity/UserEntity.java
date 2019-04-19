package com.diving_fish.ebook.entity;

import javax.persistence.*;

@Entity
@Table(name = "usercontext")
public class UserEntity {
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

    public UserEntity() {

    }
    public void set(Integer _id, String _username, String _password, String _email, Integer _role) {
        id = _id;
        username = _username;
        password = _password;
        email = _email;
        role = _role;
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
}
