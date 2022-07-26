package com.vangelis.service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vangelis.domain.Employee;
import com.vangelis.domain.User;
import com.vangelis.doms.UserDom;
import com.vangelis.repository.EmployeeRepository;
import com.vangelis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService implements IUserService
{
    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService()
    {
        int strength = 10;
        bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());

    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public User getUser(Long id)
    {
        User user = userRepository.findById(id).orElseThrow();
        return user;
    }

    public User createUser(UserDom newUser) throws RuntimeException
    {
        if(newUser.getUserName() == null)
            throw new RuntimeException("Username must not be empty");

        if(newUser.getPassword() == null)
            throw new RuntimeException("Password must not be empty");

        if(newUser.getEmail() == null)
            throw new RuntimeException("Email must not be empty");

        if(!validatePassword(newUser.getPassword()))
            throw new RuntimeException("Password does not comply with security standards");

        if(!validateUsername(newUser.getUserName()))
            throw new RuntimeException("Username does not comply with naming standards");

        String encodedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());

        User user = new User(newUser.getUserName(), encodedPassword, newUser.getEmail(), newUser.getPhone());
        userRepository.save(user);

        return user;
    }

    public User updateUser(Long id, UserDom updatedUser)
    {
        User user = userRepository.findById(id).orElseThrow();

        if (updatedUser.getUserName() != null && validateUsername(updatedUser.getUserName())) user.setUserName(updatedUser.getUserName());
        if (updatedUser.getPassword() != null && validatePassword(updatedUser.getPassword())) user.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
        if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());
        if (updatedUser.getPhone() != null) user.setPhone(updatedUser.getPhone());

        userRepository.save(user);

        return user;
    }

    private boolean validateUsername(String username)
    {
        return true;
    }

    private boolean validatePassword(String password)
    {
        return true;
    }

    public User setAvatar (Long id, MultipartFile avatar) throws IOException
    {
        if(avatar.isEmpty()) throw new RuntimeException("Avatar must not be empty");

        User user = userRepository.findById(id).orElseThrow();

        user.setUserAvatar(avatar.getBytes());

        userRepository.save(user);

        return user;
    }
}
