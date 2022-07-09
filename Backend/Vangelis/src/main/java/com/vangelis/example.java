package com.vangelis;

import com.vangelis.controller.UserController;
import com.vangelis.repository.UserRepository;

public class example {
    static UserController userController = new UserController();

    public static void main(String[] args) {
        try {
            userController.getUser(1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
