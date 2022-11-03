package com.vangelis.controller;

import com.vangelis.domain.MediaProvider;
import com.vangelis.domain.User;
import com.vangelis.doms.BioDom;
import com.vangelis.doms.StringListDom;
import com.vangelis.security.jwt.JwtTokenUtil;
import com.vangelis.service.MediaService;
import com.vangelis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/media")
public class MediaController
{
    private static final Logger log = LoggerFactory.getLogger(MediaController.class);
    private final MediaService mediaService;
    private final JwtTokenUtil jwtTokenUtil;

    public MediaController(MediaService mediaService, JwtTokenUtil jwtTokenUtil)
    {
        this.mediaService = mediaService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/{provider}/user")
    public ResponseEntity<?> AddVideoToUser(HttpServletRequest req,
                                            @PathVariable(value = "provider") MediaProvider provider,
                                            @RequestBody StringListDom videoList)
    {
        String token = req.getHeader("Authorization").split(" ")[1];
        String userName = jwtTokenUtil.getUsernameFromToken(token);

        try
        {
            User user = mediaService.addVideosToUser(userName, videoList.getStringList(), provider);
            return ResponseEntity.ok(user);
        }catch (Exception e)
        {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @PostMapping("/removeVideo")
    public ResponseEntity<?> RemoveVideoFromUser(HttpServletRequest req,
                                            @RequestBody BioDom idToRemove)
    {
        String token = req.getHeader("Authorization").split(" ")[1];
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        try
        {
            User user = mediaService.removeVideoFromUser(userName, Long.parseLong(idToRemove.getBio()));
            return ResponseEntity.ok(user);
        }catch (Exception e)
        {
            return ResponseEntity.internalServerError().body(e);
        }
    }
}
