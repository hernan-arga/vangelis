package com.vangelis.service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import com.vangelis.domain.Genre;
import com.vangelis.domain.Instrument;
import com.vangelis.domain.User;
import com.vangelis.doms.InstrumentListDom;
import com.vangelis.doms.UserDom;
import com.vangelis.repository.InstrumentReporitory;
import com.vangelis.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService implements IUserService {
    final UserRepository userRepository;
    final InstrumentReporitory instrumentReporitory;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, InstrumentReporitory instrumentReporitory) {
        int strength = 10;
        bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());

        this.userRepository = userRepository;
        this.instrumentReporitory = instrumentReporitory;
    }

    public List<User> getAllUsers(int page, int limit) {
        return userRepository.findAll(PageRequest.of(page, limit)).getContent();
    }

    public List<User> getAllUsersByInstrument(List<Long> instruments, int page, int limit)
    {return userRepository.getUsersByInstrument(instruments, PageRequest.of(page, limit));}

    public List<User> getAllUsersByGenre(List<Long> genres, int page, int limit) {return userRepository.getUsersByGenre(genres, PageRequest.of(page, limit));}

    public List<User> getAllUsersByInstrumentAndGenre(List<Long> instruments, List<Long> genres, int page, int limit)
    {return userRepository.getUsersByInstrumentAndGenre(instruments, genres, PageRequest.of(page, limit));}

    public User getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return user;
    }

    public User createUser(UserDom newUser) throws RuntimeException {
        if (newUser.getUserName() == null)
            throw new RuntimeException("Username must not be empty");

        if (newUser.getPassword() == null)
            throw new RuntimeException("Password must not be empty");

        if (newUser.getEmail() == null)
            throw new RuntimeException("Email must not be empty");

        if (!validatePassword(newUser.getPassword()))
            throw new RuntimeException("Password does not comply with security standards");

        if (!validateUsername(newUser.getUserName()))
            throw new RuntimeException("Username does not comply with naming standards");

        String encodedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());

        User user = new User(newUser.getUserName(), encodedPassword, newUser.getEmail(), newUser.getPhone());
        userRepository.save(user);

        return user;
    }

    public User updateUser(Long id, UserDom updatedUser) {
        User user = userRepository.findById(id).orElseThrow();

        if (updatedUser.getUserName() != null && validateUsername(updatedUser.getUserName()))
            user.setUserName(updatedUser.getUserName());
        if (updatedUser.getPassword() != null && validatePassword(updatedUser.getPassword()))
            user.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
        if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());
        if (updatedUser.getPhone() != null) user.setPhone(updatedUser.getPhone());

        userRepository.save(user);

        return user;
    }

    private boolean validateUsername(String username) {
        return true;
    }

    private boolean validatePassword(String password) {
        return true;
    }

    public User setAvatar(Long id, MultipartFile avatar) throws IOException {
        if (avatar.isEmpty()) throw new RuntimeException("Avatar must not be empty");

        User user = userRepository.findById(id).orElseThrow();

        user.setUserAvatar(avatar.getBytes());

        userRepository.save(user);

        return user;
    }

    public User addInstruments(Long id, InstrumentListDom instrumentList) {
        List<Instrument> instruments = instrumentReporitory.findAllById(instrumentList.getInstrumentList());
        if (instruments.size() == 0) throw new RuntimeException("None Instrument Found");

        User user = userRepository.findById(id).orElseThrow();
        user.getInstruments().addAll(instruments);
        userRepository.save(user);

        return user;
    }

    public User removeInstruments(Long id, InstrumentListDom instrumentList)
    {
        List<Instrument> instruments = instrumentReporitory.findAllById(instrumentList.getInstrumentList());
        if (instruments.size() == 0) throw new RuntimeException("None Instrument Found");

        User user = userRepository.findById(id).orElseThrow();
        user.getInstruments().removeAll(instruments);
        userRepository.save(user);

        return user;
    }

    public User addGenresToFavourites(Long id, List<String> genreList)
    {
        if(genreList.size() == 0) throw new RuntimeException("Must atleast send one Genre");

        List<Genre> genres = new ArrayList<>();
        genreList.forEach(g -> genres.add(Genre.valueOf(g)));

        User user = userRepository.findById(id).orElseThrow();
        user.getFavoriteGenres().addAll(genres);
        userRepository.save(user);

        return user;
    }

    public User removeGenresFromFavourites(Long id, List<String> genreList)
    {
        if(genreList.size() == 0) throw new RuntimeException("Must atleast send one Genre");

        List<Genre> genres = new ArrayList<>();
        genreList.forEach(g -> genres.add(Genre.valueOf(g)));

        User user = userRepository.findById(id).orElseThrow();
        user.getFavoriteGenres().removeAll(genres);
        userRepository.save(user);

        return user;
    }

    public User addGenresToBlackList(Long id, List<String> genreList)
    {
        if(genreList.size() == 0) throw new RuntimeException("Must atleast send one Genre");

        List<Genre> genres = new ArrayList<>();
        genreList.forEach(g -> genres.add(Genre.valueOf(g)));

        User user = userRepository.findById(id).orElseThrow();
        user.getBlackListGenres().addAll(genres);
        userRepository.save(user);

        return user;
    }

    public User removeGenresFromBlackList(Long id, List<String> genreList)
    {
        if(genreList.size() == 0) throw new RuntimeException("Must atleast send one Genre");

        List<Genre> genres = new ArrayList<>();
        genreList.forEach(g -> genres.add(Genre.valueOf(g)));

        User user = userRepository.findById(id).orElseThrow();
        user.getBlackListGenres().removeAll(genres);
        userRepository.save(user);

        return user;
    }
}