package com.vangelis.domain;

import javax.persistence.*;

@Entity
@Table(name = "USERS_PROFILE")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_phone")
    private String phone;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="profile_picture")
    private byte[] profilePicture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User() {
    }

    public User(String userName, String email, String phone) {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }

    public User(String userName, String email, String phone, byte[] profilePicture) {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.profilePicture = profilePicture;
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

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String toString() {
        return "Todo{id=" + this.id + ", name='" + this.userName + "', email='" + this.email + "', phone=" + this.phone + "}";
    }
}
