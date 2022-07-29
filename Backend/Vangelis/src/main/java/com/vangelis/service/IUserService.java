package com.vangelis.service;

import com.vangelis.domain.User;
import com.vangelis.doms.InstrumentListDom;
import com.vangelis.doms.UserDom;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService
{
    List<User> getAllUsers();
    User getUser(Long id);
    User createUser(UserDom newUser) throws RuntimeException;
    User updateUser(Long id, UserDom updatedUser);
    User setAvatar (Long id, MultipartFile avatar) throws IOException;
    User addInstruments(Long id, InstrumentListDom instrumentList);
    User removeInstruments(Long id, InstrumentListDom instrumentList);
    User addGenresToFavourites(Long id, List<String> genreList);
    User removeGenresFromFavourites(Long id, List<String> genreList);
    User addGenresToBlackList(Long id, List<String> genreList);
    User removeGenresFromBlackList(Long id, List<String> genreList);
}
