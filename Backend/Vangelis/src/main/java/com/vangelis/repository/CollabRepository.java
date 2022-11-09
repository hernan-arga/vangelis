package com.vangelis.repository;

import com.vangelis.domain.Collaboration;
import com.vangelis.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollabRepository extends JpaRepository<Collaboration, Long>
{


    Optional<Collaboration> findById(long id);

    Page<Collaboration> findAllByUserId(long id, Pageable pageable);

    @Query(value = "SELECT * FROM collabs WHERE (id IN (SELECT collaboration_id FROM collabs_instruments WHERE instruments_id in :instruments)) OR (id IN (SELECT collaboration_id FROM collabs_genres WHERE genres_id in :genres))", nativeQuery = true)
    Page<Collaboration> findAllFiltered(@Param("instruments") List<Long> instruments, @Param("genres") List<Long> genres, Pageable pageable);

    @Query(value = "SELECT * FROM collabs WHERE (id IN (SELECT collaboration_id FROM collabs_responses WHERE (responses_id IN (SELECT id FROM responses WHERE user_id =:userId))))", nativeQuery = true)
    List<Collaboration> findCollabsUserResponded(@Param("userId") Long userId);

}
