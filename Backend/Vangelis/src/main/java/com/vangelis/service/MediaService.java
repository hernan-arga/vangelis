package com.vangelis.service;

import com.vangelis.domain.MediaObject;
import com.vangelis.domain.MediaProvider;
import com.vangelis.domain.User;
import com.vangelis.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MediaService
{
    private final UserRepository userRepository;

    public MediaService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public User getCurrentUser(String userName)
    {
        return userRepository.findByUserName(userName).orElseThrow();
    }

    public User addVideosToUser(String userName, List<String> videos, MediaProvider provider)
    {
        User user = getCurrentUser(userName);
        List<MediaObject> mediaObjects = new ArrayList<>();

        for(String video: videos)
        {
            mediaObjects.add(new MediaObject(provider, video));
        }

        user.addVideos(mediaObjects);
        userRepository.save(user);

        return user;
    }
}
