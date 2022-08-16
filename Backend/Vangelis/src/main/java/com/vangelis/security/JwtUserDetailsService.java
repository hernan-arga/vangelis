package com.vangelis.security;

import com.vangelis.domain.User;
import com.vangelis.doms.UserDom;
import com.vangelis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<User> user = userRepository.findByUserName(username);
        if(user.isPresent()) return user.get();

        throw new UsernameNotFoundException("Username " + username + " not found");
    }

    public User save(UserDom userDom)
    {
        User newUser = new User(userDom.getUserName(), bcryptEncoder.encode(userDom.getPassword()), userDom.getEmail(), userDom.getPhone());
        userRepository.save(newUser);

        return newUser;
    }
}