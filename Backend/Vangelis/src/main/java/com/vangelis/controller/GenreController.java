package com.vangelis.controller;

import com.vangelis.domain.Genre;
import com.vangelis.domain.Instrument;
import com.vangelis.repository.GenreRepository;
import com.vangelis.repository.InstrumentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController
{
    final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository)
    {
        this.genreRepository = genreRepository;
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAll()
    {
        try
        {
            List<Genre> genres = genreRepository.findAll();
            return new ResponseEntity(genres, HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getOne(@PathVariable Long id)
    {
        try
        {
            Genre genres = genreRepository.findById(id).orElseThrow();
            return new ResponseEntity(genres, HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }
}
