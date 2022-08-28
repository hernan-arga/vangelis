package com.vangelis.controller;

import com.vangelis.domain.Suggestion;
import com.vangelis.repository.SuggestionsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/suggestions")
public class SuggestionsController
{
    final SuggestionsRepository suggestionsRepository;

    public SuggestionsController(SuggestionsRepository suggestionsRepository) {
        this.suggestionsRepository = suggestionsRepository;
    }

    @PostMapping("/instruments")
    public ResponseEntity addInstrumentSuggestion(@RequestBody Suggestion suggestion)
    {
        if(!suggestion.getType().equals("INSTRUMENT")) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        if(suggestion.getName() == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        if(suggestion.getUserId() == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        suggestion.setName(suggestion.getName().toUpperCase(Locale.ROOT));

        try
        {
            suggestionsRepository.save(suggestion);
            return new ResponseEntity(HttpStatus.CREATED);
        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/genres")
    public ResponseEntity addGenreSuggestion(@RequestBody Suggestion suggestion)
    {
        if(!suggestion.getType().equals("GENRE")) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        if(suggestion.getName() == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        if(suggestion.getUserId() == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        suggestion.setName(suggestion.getName().toUpperCase(Locale.ROOT));

        try
        {
            suggestionsRepository.save(suggestion);
            return new ResponseEntity(HttpStatus.CREATED);
        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Suggestion>> getAll()
    {
        return new ResponseEntity<>(suggestionsRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/instruments")
    public ResponseEntity<List<Suggestion>> getAllInstrumentSuggestions()
    {
        return new ResponseEntity<>(suggestionsRepository.findSuggestionsByType("INSTRUMENT"), HttpStatus.OK);
    }

    @GetMapping("/genres")
    public ResponseEntity<List<Suggestion>> getAllGenreSuggestions()
    {
        return new ResponseEntity<>(suggestionsRepository.findSuggestionsByType("GENRE"), HttpStatus.OK);
    }
}
