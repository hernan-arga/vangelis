package com.vangelis.service;

import com.vangelis.domain.*;
import com.vangelis.doms.CollabResponseDom;
import com.vangelis.doms.CollaborationDom;
import com.vangelis.doms.ErrorResponse;
import com.vangelis.doms.UserDom;
import com.vangelis.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CollabService
{
    private final CollabRepository collabRepository;
    private final ResponseRepository responseRepository;
    private final UserRepository userRepository;
    private final InstrumentRepository instrumentRepository;
    private final GenreRepository genreRepository;

    private final MediaRepository mediaRepository;


    public CollabService(CollabRepository collabRepository,UserRepository userRepository,
                         InstrumentRepository instrumentRepository, GenreRepository genreRepository,
                         MediaRepository mediaRepository, ResponseRepository responseRepository)
    {
        this.collabRepository = collabRepository;
        this.userRepository = userRepository;
        this.instrumentRepository = instrumentRepository;
        this.genreRepository = genreRepository;
        this.mediaRepository = mediaRepository;
        this.responseRepository = responseRepository;
    }

    public List<Collaboration> searchMyClosedCollabs(long currentUserId)
    {
        return collabRepository.findAll().stream()
            .filter(c ->
                    (c.getUser().getId()==currentUserId || c.getResponses().stream().anyMatch(r -> r.getUser().getId()==currentUserId && r.isWinner()))
                            && !c.isOpen()).collect(Collectors.toList());
    }

    public List<Collaboration> searchMyCollabs(List<Long> instruments, List<Long> genres, long currentUserId, int page, int limit)
    {
        if(instruments == null && genres == null)
            return collabRepository.findAll().stream()
                    .filter(c -> c.getUser().getId()==currentUserId).collect(Collectors.toList());
        if(instruments == null) instruments = instrumentRepository.getAllIds();
        if(genres == null) genres = genreRepository.getAllIds();
        return collabRepository.findAllFiltered(instruments, genres,
                PageRequest.of(page, limit)).getContent()
                .stream().filter(c -> c.getUser().getId()==currentUserId).collect(Collectors.toList());

    }
    public List<Collaboration> searchCollabs(List<Long> instruments, List<Long> genres,long currentUserId, int page, int limit)
    {
        if(instruments == null && genres == null)
            return collabRepository.findAll().stream()
                    .filter(c->c.getUser().getId()!=currentUserId).collect(Collectors.toList());
        if(instruments == null) instruments = instrumentRepository.getAllIds();
        if(genres == null) genres = genreRepository.getAllIds();

        return collabRepository.findAllFiltered(instruments, genres,
                PageRequest.of(page, limit)).getContent()
                .stream().filter(c->c.getUser().getId()!=currentUserId).collect(Collectors.toList());
    }

    public List<Collaboration> getCollabsUserResponded(Long userId){
        return collabRepository.findCollabsUserResponded(userId);
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

    public boolean createCollaborationRequest(User user, String title, String description, Set<Long> genres, Set<Long> instruments, MediaObject mediaObject)
    {
        Set<Genre> genreList = Set.copyOf(genreRepository.findAllById(genres));
        Set<Instrument> instrumentList = Set.copyOf(instrumentRepository.findAllById(instruments));

        Optional<MediaObject> existingMedia = mediaRepository.getByMediaUrl(mediaObject.getMediaUrl());
        if (existingMedia.isEmpty()){
            mediaObject = mediaRepository.save(mediaObject);
        }else{
            mediaObject = existingMedia.get();
        }

        Collaboration collaboration = new Collaboration(title, genreList, instrumentList, description, user, mediaObject);

        collabRepository.save(collaboration);

        return true;
    }

    public boolean createCollaborationResponse(User user, Long collabId, MediaObject mediaObject)
    {
        Collaboration collaboration = collabRepository.findById(collabId).orElseThrow();

        Optional<MediaObject> existingMedia = mediaRepository.getByMediaUrl(mediaObject.getMediaUrl()).stream().findFirst();
        if (existingMedia.isEmpty()){
            mediaObject = mediaRepository.save(mediaObject);
        }else{
            mediaObject = existingMedia.get();
        }

        CollabResponse collabResponse = new CollabResponse(user, mediaObject);

        collaboration.addResponse(collabResponse);

        collabRepository.save(collaboration);

        return true;
    }

    public boolean chooseCollabWinner(long collabId, long responseId){
        Collaboration collaboration = collabRepository.findById(collabId).orElseThrow();
        CollabResponse response = responseRepository.findById(responseId).orElseThrow();

        collaboration.setOpen(false);
        response.setWinner(true);

        collabRepository.save(collaboration);
        responseRepository.save(response);

        return true;
    }

}