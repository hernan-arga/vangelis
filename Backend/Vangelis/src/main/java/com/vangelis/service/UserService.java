package com.vangelis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vangelis.domain.User;
import com.vangelis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    List<User> userList = new ArrayList();
    @Autowired
    UserRepository userRepository;

    @Autowired
    public UserService() throws Exception {
        User userA = new User(1L, "Juan Carlos", "juan@gmail.com", "1159682493");
        User userB = new User(1L, "Miguel Mineros", "miguel@gmail.com", "1129333491");
        this.userList = List.of(userA, userB);
    }

    public User getUser(long id) {
        return (User)((List)this.userList.stream().filter((p) -> {
            return p.getId() == id;
        }).collect(Collectors.toList())).get(0);
    }

    public User prueba() throws Exception {
        return this.userRepository.prueba();
    }
}
