package com.vangelis.controller;

import com.vangelis.domain.Genre;
import com.vangelis.domain.Instrument;
import com.vangelis.domain.User;
import com.vangelis.doms.BioDom;
import com.vangelis.doms.GenreListDom;
import com.vangelis.doms.LongListDom;
import com.vangelis.doms.UserDom;
import com.vangelis.security.jwt.JwtTokenUtil;
import com.vangelis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController
{
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    public UserController(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(
            HttpServletRequest req,
            @RequestParam(value = "instruments", required = false) List<Long> instruments,
            @RequestParam(value = "genres", required = false) List<Long> genres,
            @RequestParam(value = "username", required = false) String userName,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit)
    {
        if(page == null) page = 0;
        if(limit == null) limit = 25;
        if(userName == null) userName = "";

        String token = req.getHeader("Authorization").split(" ")[1];
        String currentUserName = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.getCurrentUser(currentUserName);

        List<User> users = userService.getAllUsers(instruments, genres, userName, page, limit);
        users = users.stream().filter(user1 -> user1.getId() != user.getId()).collect(Collectors.toList());
        users.sort(new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                List<Instrument> instrumentsInCommon1 = user1
                        .getInstruments().stream()
                        .filter(instrument -> user.getInstruments()
                                .contains(instrument)).toList();
                List<Instrument> instrumentsInCommon2 = user2
                        .getInstruments().stream()
                        .filter(instrument -> user.getInstruments()
                                .contains(instrument)).toList();
                List<Genre> genresInCommon1 = user1
                        .getFavoriteGenres().stream()
                        .filter(genre -> user.getFavoriteGenres()
                                .contains(genre)).toList();
                List<Genre> genresInCommon2 = user2
                        .getFavoriteGenres().stream()
                        .filter(genre -> user.getFavoriteGenres()
                                .contains(genre)).toList();

                int val1 = instrumentsInCommon1.size() + genresInCommon1.size();
                int val2 = genresInCommon2.size() + instrumentsInCommon2.size();

                if (val1 == val2)
                    return 0;
                else if (val1 < val2)
                    return 1;
                else //if(val1 > val2)
                    return -1;
            }
        });

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/favorites/{id}")
    public ResponseEntity<List<User>> getFavoriteUsers(@PathVariable Long id)
    {
        List<User> users = new ArrayList<>();
        users = userService.getFavoriteUsersOf(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest req)
    {
        try
        {
            String token = req.getHeader("Authorization").split(" ")[1];
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            User user = userService.getCurrentUser(userName);
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

    @RequestMapping(value = "/photo", method = RequestMethod.PATCH, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPhoto(HttpServletRequest req, @RequestParam("file") MultipartFile photo)
    {
        try
        {
            String token = req.getHeader("Authorization").split(" ")[1];
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            User user = userService.uploadPhoto(userService.getCurrentUser(userName), photo);

            return ResponseEntity.ok(user);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/removePhoto")
    public ResponseEntity<?> RemovePhoto(HttpServletRequest req,
                                                 @RequestBody BioDom idToRemove)
    {
        String token = req.getHeader("Authorization").split(" ")[1];
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        try
        {
            User user = userService.removePhotoFromUser(userName, Long.parseLong(idToRemove.getBio()));
            return ResponseEntity.ok(user);
        }catch (Exception e)
        {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @RequestMapping(value = "/avatars", method = RequestMethod.PATCH, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> setUserAvatar(HttpServletRequest req, @RequestParam("file") MultipartFile avatar)
    {
        try
        {
            String token = req.getHeader("Authorization").split(" ")[1];
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            User user = userService.setAvatar(userService.getCurrentUser(userName), avatar);

            return ResponseEntity.ok(user);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PatchMapping("/instruments")
    public ResponseEntity<?> addInstruments(HttpServletRequest req, @RequestBody LongListDom instrumentList)
    {
        try
        {
            String token = req.getHeader("Authorization").split(" ")[1];
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            User user = userService.setInstruments(userService.getCurrentUser(userName), instrumentList.getLongList());

            return ResponseEntity.ok(user);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PatchMapping("/genres")
    public ResponseEntity<?> addGenresToFavourites(HttpServletRequest req, @RequestBody GenreListDom genreList)
    {
        try
        {
            String token = req.getHeader("Authorization").split(" ")[1];
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            User user = userService.setFavouriteGenres(userService.getCurrentUser(userName), genreList.getGenres());

            return ResponseEntity.ok(user);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }
    @PatchMapping("/favorites")
    public ResponseEntity<?> addUserToFavorites(HttpServletRequest req, @RequestBody LongListDom favoriteList)
    {
        try
        {
            String token = req.getHeader("Authorization").split(" ")[1];
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            User user = userService.setFavouriteUsers(userService.getCurrentUser(userName), favoriteList.getLongList());

            return ResponseEntity.ok(user);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }
    @PatchMapping("/description")
    public ResponseEntity<?> updateBio(HttpServletRequest req, @RequestBody BioDom newBio)
    {
        try
        {
            String token = req.getHeader("Authorization").split(" ")[1];
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            User user = userService.updateBio(userService.getCurrentUser(userName), newBio.getBio());

            return ResponseEntity.ok(user);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PatchMapping("/phonenumber")
    public ResponseEntity<?> updatePhone(HttpServletRequest req, @RequestBody BioDom newPhone)
    {
        try
        {
            String token = req.getHeader("Authorization").split(" ")[1];
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            User user = userService.updatePhone(userService.getCurrentUser(userName), newPhone.getBio());

            return ResponseEntity.ok(user);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PatchMapping("/email")
    public ResponseEntity<?> updateEmail(HttpServletRequest req, @RequestBody BioDom newEmail)
    {
        try
        {
            String token = req.getHeader("Authorization").split(" ")[1];
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            User user = userService.updateEmail(userService.getCurrentUser(userName), newEmail.getBio());

            return ResponseEntity.ok(user);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PatchMapping
    public ResponseEntity<?> editUser(HttpServletRequest req, @RequestBody UserDom userDom)
    {
        try
        {
            String token = req.getHeader("Authorization").split(" ")[1];
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            User user = userService.editUser(userService.getCurrentUser(userName), userDom.getUserName(), userDom.getPassword(), userDom.getBio(), userDom.getUserPhone());

            return ResponseEntity.ok(user);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
