package com.vangelis.service;

import com.vangelis.domain.Genre;
import com.vangelis.domain.Instrument;
import com.vangelis.domain.User;
import com.vangelis.doms.ErrorResponse;
import com.vangelis.doms.UserDom;
import com.vangelis.repository.GenreRepository;
import com.vangelis.repository.InstrumentRepository;
import com.vangelis.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService
{
    final UserRepository userRepository;
    final InstrumentRepository instrumentRepository;
    final GenreRepository genreRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, InstrumentRepository instrumentRepository, GenreRepository genreRepository)
    {
        int strength = 10;
        bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());

        this.userRepository = userRepository;
        this.instrumentRepository = instrumentRepository;
        this.genreRepository = genreRepository;
    }

    public List<User> getAllUsers(List<Long> instruments, List<Long> genres, int page, int limit)
    {
        if(instruments == null && genres == null) return userRepository.findAll(PageRequest.of(page, limit)).getContent();

        if(instruments == null) instruments = instrumentRepository.getAllIds();
        if(genres == null) genres = genreRepository.getAllIds();

        return userRepository.findAllFiltered(instruments, genres, PageRequest.of(page, limit)).getContent();
    }

    public User getUser(Long id)
    {
        User user = userRepository.findById(id).orElseThrow();
        return user;
    }

    public User createUser(UserDom newUser) throws RuntimeException
    {
        if (newUser.getUserName() == null)
            throw new ErrorResponse("U001", "Bad Request", "Username must not be empty", new Date());

        if (newUser.getPassword() == null)
            throw new ErrorResponse("U002", "Bad Request", "Password must not be empty", new Date());

        if (newUser.getEmail() == null)
            throw new ErrorResponse("U003", "Bad Request", "Email must not be empty", new Date());

        if(!validateUsername(newUser.getUserName()))
            throw new ErrorResponse("U004", "Invalid username", "Username does not comply with naming policies", new Date());

        if (!validatePassword(newUser.getPassword()))
            throw new ErrorResponse("U005", "Invalid password", "Password does not comply with security standards", new Date());

        String encodedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());

        User user = new User(newUser.getUserName(), encodedPassword, newUser.getEmail());
        userRepository.save(user);

        return user;
    }

    /*
    Username requirements:
        Username must be unique
        Username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase.
        Username allowed of the dot (.), underscore (_), and hyphen (-).
        The dot (.), underscore (_), or hyphen (-) must not be the first or last character.
        The dot (.), underscore (_), or hyphen (-) does not appear consecutively, e.g., java..regex
        The number of characters must be between 5 and 20.
    */
    public boolean validateUsername(String username)
    {
        Optional<User> user = userRepository.findByUserName(username);

        if(user.isPresent()) throw new ErrorResponse("U006", "Invalid username", "Username already exists", new Date());

        String USERNAME_PATTERN = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

        Pattern pattern = Pattern.compile(USERNAME_PATTERN);

        Matcher matcher = pattern.matcher(username);

        if(matcher.matches()) return true;

        throw new ErrorResponse("U007", "Invalid username", "Username does not comply with naming policies", new Date());
    }

    /*
    Secure Password requirements:
        Password must contain at least one digit [0-9].
        Password must contain at least one lowercase Latin character [a-z].
        Password must contain at least one uppercase Latin character [A-Z].
        Password must contain at least one special character like ! @ # & ( ).
        Password must contain a length of at least 8 characters and a maximum of 20 characters.
    */
    public boolean validatePassword(String password)
    {
        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–_[{}]:;',?/*~$^+=<>]).{8,20}$";

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public User setAvatar(MultipartFile avatar) throws IOException
    {
        if (avatar.isEmpty()) throw new ErrorResponse("U008", "Bad Request", "Avatar must not be empty", new Date());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setUserAvatar(avatar.getBytes());

        userRepository.save(user);

        return user;
    }

    public User setInstruments(List<Long> instrumentList)
    {
        List<Instrument> instruments = instrumentRepository.findAllById(instrumentList);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setInstruments(Set.copyOf(instruments));
        userRepository.save(user);
        return user;
    }

    public User setFavouriteGenres(List<Long> genreList)
    {
        List<Genre> genres = genreRepository.findAllById(genreList);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setFavoriteGenres(Set.copyOf(genres));
        userRepository.save(user);
        return user;
    }

    public User editUser(String username, String password, String bio)
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(username != null)
        {
            validateUsername(username);
            user.setUserName(username);
        }

        if(password != null && validatePassword(password))
        {
            String encodedPassword = bCryptPasswordEncoder.encode(password);
            user.setPassword(encodedPassword);
        }

        if(bio != null)
        {
            user.setBio(bio);
        }

        userRepository.save(user);
        return user;
    }
}