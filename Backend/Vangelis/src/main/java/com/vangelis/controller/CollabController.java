package com.vangelis.controller;

import com.vangelis.domain.Collaboration;
import com.vangelis.domain.Instrument;
import com.vangelis.domain.User;
import com.vangelis.repository.CollabRepository;
import com.vangelis.repository.GenreRepository;
import com.vangelis.repository.InstrumentRepository;
import com.vangelis.service.CollabService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collabs")
public class CollabController
{
    //final CollabRepository collabRepository;
    final CollabService collabService;

    public CollabController(CollabService collabService)
    {
        this.collabService = collabService;
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
}
