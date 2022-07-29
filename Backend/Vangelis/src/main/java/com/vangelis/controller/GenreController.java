package com.vangelis.controller;

import com.vangelis.domain.Genre;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genres")
public class GenreController
{
    @GetMapping
    public ResponseEntity<Genre[]> getAll()
    {
        return new ResponseEntity(Genre.values(), HttpStatus.OK);
    }
}
