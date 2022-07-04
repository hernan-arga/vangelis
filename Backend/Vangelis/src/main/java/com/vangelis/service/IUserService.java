package com.vangelis.service;

import com.vangelis.domain.User;

public interface IUserService {
    User getUser(long id);

    User prueba() throws Exception;
}
