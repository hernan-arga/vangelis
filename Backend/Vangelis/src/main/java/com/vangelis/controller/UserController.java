package com.vangelis.controller;

import com.vangelis.domain.Genre;
import com.vangelis.domain.User;
import com.vangelis.doms.GenreListDom;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController
{
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(
            @RequestParam(value = "instruments", required = false) List<Long> instruments,
            @RequestParam(value = "genres", required = false) List<Genre> genres,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit)
    {
        if(page == null) page = 0;
        if(limit == null) limit = 25;

        try
        {
            if(instruments == null && genres == null)
            {
                List<User> users = userService.getAllUsers(page, limit);
                return new ResponseEntity(users, HttpStatus.OK);
            }else if(instruments == null)
            {
                List<Long> genresList = genres.stream().map(g -> Long.valueOf(g.ordinal())).collect(Collectors.toList());
                List<User> users = userService.getAllUsersByGenre(genresList, page, limit);
                return new ResponseEntity(users, HttpStatus.OK);
            }else if(genres == null)
            {
                List<User> users = userService.getAllUsersByInstrument(instruments, page, limit);
                return new ResponseEntity(users, HttpStatus.OK);
            }else
            {
                List<Long> genresList = genres.stream().map(g -> Long.valueOf(g.ordinal())).collect(Collectors.toList());
                List<User> users = userService.getAllUsersByInstrumentAndGenre(instruments, genresList, page, limit);
                return new ResponseEntity(users, HttpStatus.OK);
            }
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

    @PostMapping("/{id}/genres/favourites")
    public ResponseEntity<User> addGenresToFavourites(@PathVariable Long id, @RequestBody GenreListDom genreList)
    {
        try
        {
            User user = userService.addGenresToFavourites(id, genreList.getGenres());

            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/genres/favourites")
    public ResponseEntity<User> removeGenresFromFavourites(@PathVariable Long id, @RequestBody GenreListDom genreList)
    {
        try
        {
            User user = userService.removeGenresFromFavourites(id, genreList.getGenres());

            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/genres/black_list")
    public ResponseEntity<User> addGenresToBlackList(@PathVariable Long id, @RequestBody GenreListDom genreList)
    {
        try
        {
            User user = userService.addGenresToBlackList(id, genreList.getGenres());

            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/genres/black_list")
    public ResponseEntity<User> removeGenresFromBlackList(@PathVariable Long id, @RequestBody GenreListDom genreList)
    {
        try
        {
            User user = userService.removeGenresFromBlackList(id, genreList.getGenres());

            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
}
