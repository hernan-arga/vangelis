package com.vangelis.controller;

import com.vangelis.domain.*;
import com.vangelis.doms.BioDom;
import com.vangelis.doms.CollabWInnerDom;
import com.vangelis.doms.CollaborationDom;
import com.vangelis.doms.CollabResponseDom;
import com.vangelis.security.jwt.JwtTokenUtil;
import com.vangelis.service.CollabService;
import com.vangelis.service.MediaService;
import com.vangelis.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/collabs")
public class CollabController
{
    //final CollabRepository collabRepository;
    final CollabService collabService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    private final MediaService mediaService;

    public CollabController(CollabService collabService, JwtTokenUtil jwtTokenUtil, UserService userService, MediaService mediaService)
    {
        this.collabService = collabService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.mediaService = mediaService;
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

    @GetMapping("/responses")
    public ResponseEntity<?> getCollabsUserResponsed( HttpServletRequest req)
    {
        String token = req.getHeader("Authorization").split(" ")[1];
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.getCurrentUser(userName);
        long id = user.getId();

        List<Collaboration> collaborations = collabService.getCollabsUserResponded(id);

        return new ResponseEntity<>(collaborations, HttpStatus.OK);
    }

    @GetMapping("/myclosedcollabs")
    public ResponseEntity<?> getClosedCollabs(HttpServletRequest req)
    {
        String token = req.getHeader("Authorization").split(" ")[1];
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.getCurrentUser(userName);
        long id = user.getId();
        try
        {
            List<Collaboration> collaborations = collabService.searchMyClosedCollabs(id);
            return new ResponseEntity<>(collaborations, HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Collaboration>> searchCollabs(
            HttpServletRequest req,
            @RequestParam(value = "instruments", required = false) List<Long> instruments,
            @RequestParam(value = "bringMyCollabs", required = true) boolean bringMyCollabs,
            @RequestParam(value = "genres", required = false) List<Long> genres,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit)
    {
        if(page == null) page = 0;
        if(limit == null) limit = 25;


        String token = req.getHeader("Authorization").split(" ")[1];
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.getCurrentUser(userName);
        long id = user.getId();
        List<Collaboration> collaborations = bringMyCollabs?
                collabService.searchMyCollabs(instruments, genres,id, page, limit)
                : collabService.searchCollabs(instruments, genres,id, page, limit);


        if(bringMyCollabs)
        {
            //todo: ver si ordenamos las responses
        }
        else {
            collaborations.sort(new Comparator<Collaboration>() {
                @Override
                public int compare(Collaboration col1, Collaboration col2) {
                    List<Instrument> instrumentsInCommon1 = col1
                            .getInstruments().stream()
                            .filter(instrument -> user.getInstruments()
                                    .contains(instrument)).toList();
                    List<Instrument> instrumentsInCommon2 = col2
                            .getInstruments().stream()
                            .filter(instrument -> user.getInstruments()
                                    .contains(instrument)).toList();
                    List<Genre> genresInCommon1 = col1
                            .getGenres().stream()
                            .filter(genre -> user.getFavoriteGenres()
                                    .contains(genre)).toList();
                    List<Genre> genresInCommon2 = col2
                            .getGenres().stream()
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
        }

        return new ResponseEntity<>(collaborations, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> createCollabRequest(HttpServletRequest req, @RequestBody CollaborationDom newCollab)
    {
        try
        {
            String token = req.getHeader("Authorization").split(" ")[1];
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            MediaObject mediaObject = new MediaObject(newCollab.getPlatform(), newCollab.getMediaUrl());
            boolean result = collabService.createCollaborationRequest(
                    userService.getCurrentUser(userName),
                    newCollab.getTitle(),
                    newCollab.getDescription(),
                    newCollab.getGenres(),
                    newCollab.getInstruments(),
                    mediaObject);

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
                    new MediaObject(collabResponse.getPlatform(), collabResponse.getMediaUrl())
            );
            return ResponseEntity.ok().build();
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/winner")
    public ResponseEntity<?> chooseCollabWinner(@RequestBody CollabWInnerDom ids){
        try{
            collabService.chooseCollabWinner(ids.getCollabId(),ids.getResponseId());
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }
}
