package com.vangelis.service;

import com.vangelis.domain.Collaboration;
import com.vangelis.domain.Genre;
import com.vangelis.domain.Instrument;
import com.vangelis.domain.User;
import com.vangelis.doms.ErrorResponse;
import com.vangelis.doms.UserDom;
import com.vangelis.repository.CollabRepository;
import com.vangelis.repository.GenreRepository;
import com.vangelis.repository.InstrumentRepository;
import com.vangelis.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CollabService
{
    private final CollabRepository collabRepository;
    private final UserRepository userRepository;
    private final InstrumentRepository instrumentRepository;
    private final GenreRepository genreRepository;


    public CollabService(CollabRepository collabRepository,UserRepository userRepository, InstrumentRepository instrumentRepository, GenreRepository genreRepository)
    {
        this.collabRepository = collabRepository;
        this.userRepository = userRepository;
        this.instrumentRepository = instrumentRepository;
        this.genreRepository = genreRepository;
    }

    public List<Collaboration> searchCollabs(List<Long> instruments, List<Long> genres, int page, int limit)
    {
        if(instruments == null && genres == null) return collabRepository.findAll();

        if(instruments == null) instruments = instrumentRepository.getAllIds();
        if(genres == null) genres = genreRepository.getAllIds();

        return collabRepository.findAllFiltered(instruments, genres, PageRequest.of(page, limit)).getContent();
    }


    public Collaboration getCollab(Long id)
    {
        return collabRepository.findById(id).orElseThrow();
    }

    public List<Collaboration> getCollabsByUserId(Long id, int page, int limit){ return collabRepository.findAllByUserId(id,PageRequest.of(page, limit)).getContent();}

    public Collaboration setInstruments(Collaboration collaboration, List<Long> instrumentList)
    {
        List<Instrument> instruments = instrumentRepository.findAllById(instrumentList);
        Set<Instrument> instrumentsSet = new HashSet<Instrument>(instruments);
        collaboration.setInstruments(instrumentsSet);
        collabRepository.save(collaboration);
        return collaboration;
    }

    public List<Collaboration> getAll(){
        return collabRepository.findAll();
    }

    public Collaboration setFavouriteGenres(Collaboration collaboration, List<Long> genreList)
    {
        List<Genre> genres = genreRepository.findAllById(genreList);
        Set<Genre> genreSet = new HashSet<Genre>(genres);

        collaboration.setGenres(genreSet);
        collabRepository.save(collaboration);
        return collaboration;
    }

}