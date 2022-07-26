package com.vangelis.doms;

public class UserDom
{
    private String userName;
    private String password;
    private String email;
    private String phone;

    public UserDom() {}

    public UserDom(Long id, String userName, String password, String email)
    {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public UserDom(Long id, String userName, String password, String email, String phone)
    {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
