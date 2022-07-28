package com.vangelis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @JsonIgnore
    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_email", nullable = false)
    private String email;

    @Column(name = "user_phone")
    private String phone;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "user_avatar")
    private byte[] userAvatar;

    @ManyToMany(targetEntity = Instrument.class, fetch = FetchType.LAZY)
    private Set<Instrument> instruments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User() {
    }

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public User(String userName, String password, String email, String phone) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(byte[] user_avatar) {
        this.userAvatar = user_avatar;
    }

    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(Set<Instrument> instruments) {
        this.instruments = instruments;
    }

    public String toString() {
        return "Todo{id=" + this.id + ", name='" + this.userName + "', email='" + this.email + "', phone=" + this.phone + "}";
    }
}
