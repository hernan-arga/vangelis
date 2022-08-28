package com.vangelis.controller;

import com.vangelis.domain.User;
import com.vangelis.doms.GenreListDom;
import com.vangelis.doms.InstrumentListDom;
import com.vangelis.doms.UserDom;
import com.vangelis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(
            @RequestParam(value = "instruments", required = false) List<Long> instruments,
            @RequestParam(value = "genres", required = false) List<Long> genres,
            @RequestParam(value = "username", required = false) String userName,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit)
    {
        if(page == null) page = 0;
        if(limit == null) limit = 25;
        if(userName == null) userName = "";

        List<User> users = userService.getAllUsers(instruments, genres, userName, page, limit);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser()
    {
        try
        {
            User user = userService.getCurrentUser();
            return ResponseEntity.ok(user);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id)
    {
        try
        {
            User user = userService.getUser(id);
            return ResponseEntity.ok(user);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PatchMapping("/avatars")
    public ResponseEntity<?> setUserAvatar(@RequestParam("file") MultipartFile avatar)
    {
        try
        {
            User user = userService.setAvatar(avatar);

            return ResponseEntity.ok(user);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PatchMapping("/instruments")
    public ResponseEntity<?> addInstruments(@RequestBody InstrumentListDom instrumentList)
    {
        try
        {
            User user = userService.setInstruments(instrumentList.getInstrumentList());

            return ResponseEntity.ok(user);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PatchMapping("/genres")
    public ResponseEntity<?> addGenresToFavourites(@RequestBody GenreListDom genreList)
    {
        try
        {
            User user = userService.setFavouriteGenres(genreList.getGenres());

            return ResponseEntity.ok(user);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PatchMapping
    public ResponseEntity<?> editUser(@RequestBody UserDom userDom)
    {
        try
        {
            User user = userService.editUser(userDom.getUserName(), userDom.getPassword(), userDom.getBio());

            return ResponseEntity.ok(user);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
