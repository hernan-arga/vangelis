package com.vangelis.controller;

import com.vangelis.domain.Collaboration;
import com.vangelis.domain.MediaObject;
import com.vangelis.doms.CollaborationDom;
import com.vangelis.doms.CollabResponseDom;
import com.vangelis.security.jwt.JwtTokenUtil;
import com.vangelis.service.CollabService;
import com.vangelis.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/collabs")
public class CollabController
{
    //final CollabRepository collabRepository;
    final CollabService collabService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public CollabController(CollabService collabService, JwtTokenUtil jwtTokenUtil, UserService userService)
    {
        this.collabService = collabService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAll()
    {
        try
        {
            List<Collaboration> collaborations = collabService.getAll();
            return new ResponseEntity<>(collaborations, HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id)
    {
        try
        {
            Collaboration collaboration = collabService.getCollab(id);
            return new ResponseEntity<>(collaboration, HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Collaboration>> searchCollabs(
            @RequestParam(value = "instruments", required = false) List<Long> instruments,
            @RequestParam(value = "genres", required = false) List<Long> genres,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit)
    {
        if(page == null) page = 0;
        if(limit == null) limit = 25;

        List<Collaboration> collaborations = collabService.searchCollabs(instruments, genres, page, limit);

        return new ResponseEntity<>(collaborations, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCollabRequest(HttpServletRequest req, @RequestBody CollaborationDom newCollab)
    {
        try
        {
            String token = req.getHeader("Authorization").split(" ")[1];
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            boolean result = collabService.createCollaborationRequest(
                    userService.getCurrentUser(userName),
                    newCollab.getTitle(),
                    newCollab.getDescription(),
                    newCollab.getGenres(),
                    newCollab.getInstruments(),
                    new MediaObject(newCollab.getPlatform(), newCollab.getMediaUrl()));

            return ResponseEntity.ok().build();
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> createCollabResponse(HttpServletRequest req, @PathVariable Long id, @RequestBody CollabResponseDom collabResponse)
    {
        try
        {
            String token = req.getHeader("Authorization").split(" ")[1];
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            boolean result = collabService.createCollaborationResponse(
                    userService.getCurrentUser(userName),
                    id,
                    new MediaObject(collabResponse.getMediaProvider(), collabResponse.getMediaUrl())
            );
            return ResponseEntity.ok().build();
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
