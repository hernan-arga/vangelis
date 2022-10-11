package com.vangelis.repository;

import com.vangelis.domain.Genre;
import com.vangelis.domain.MediaObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<MediaObject, Long>
{
    @Query(value = "SELECT id from media_objects", nativeQuery = true)
    List<Long> getAllIds();

    Optional<MediaObject> getByMediaUrl(String mediaUrl);
}
