package com.vangelis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vangelis.domain.Employee;
import com.vangelis.domain.User;
import com.vangelis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    List<User> userList = new ArrayList();
    UserRepository userRepository;

    @Autowired
    public UserService() throws Exception {
        User userA = new User("Juan Carlos", "juan@gmail.com", "1159682493");
        User userB = new User( "Miguel Mineros", "miguel@gmail.com", "1129333491");
        this.userList = List.of(userA, userB);
        this.userRepository = new UserRepository();
    }

    public User getUser(long id) {
        return (User)((List)this.userList.stream().filter((p) -> {
            return p.getId() == id;
        }).collect(Collectors.toList())).get(0);
    }

    public Employee prueba() {
        return this.userRepository.SaveEmployee();
    }
}
