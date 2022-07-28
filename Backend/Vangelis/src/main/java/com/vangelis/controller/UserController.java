package com.vangelis.controller;

import com.vangelis.domain.User;
import com.vangelis.doms.InstrumentListDom;
import com.vangelis.doms.UserDom;
import com.vangelis.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers()
    {
        try
        {
            List<User> users = userService.getAllUsers();
            return new ResponseEntity(users, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<User>> getAllUsers(@PathVariable Long id)
    {
        try
        {
            User user = userService.getUser(id);
            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDom newUser)
    {
        try
        {
            User user = userService.createUser(newUser);
            return new ResponseEntity(user, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDom modifiedUser)
    {
        try
        {
            User user = userService.updateUser(id, modifiedUser);

            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/avatars")
    public ResponseEntity<User> setUserAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile avatar)
    {
        try
        {
            User user = userService.setAvatar(id, avatar);

            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/instruments")
    public ResponseEntity<User> addInstruments(@PathVariable Long id, @RequestBody InstrumentListDom instrumentList)
    {
        try
        {
            User user = userService.addInstruments(id, instrumentList);

            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/instruments")
    public ResponseEntity<User> removeInstruments(@PathVariable Long id, @RequestBody InstrumentListDom instrumentList)
    {
        try
        {
            User user = userService.removeInstruments(id, instrumentList);

            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
}
