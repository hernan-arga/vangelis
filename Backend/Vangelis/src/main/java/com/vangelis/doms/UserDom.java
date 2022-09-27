package com.vangelis.doms;

import com.vangelis.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDom
{
    private String userName;
    private String password;
    private String email;
    private String userPhone;
    private String bio;

    public UserDom(String userName, String password, String email)
    {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
}
