package com.vangelis.repository;

import com.vangelis.domain.CollabResponse;
import com.vangelis.domain.Collaboration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResponseRepository extends JpaRepository<CollabResponse, Long>
{


    Optional<CollabResponse> findById(long id);

    Page<CollabResponse> findAllByUserId(long id, Pageable pageable);

    @Query(value = "SELECT * FROM responses WHERE (id IN (SELECT collab_response_id FROM response_instruments WHERE instruments_id in :instruments)) OR (id IN (SELECT collab_response_id FROM response_genres WHERE genres_id in :genres))", nativeQuery = true)
    Page<CollabResponse> findAllFiltered(@Param("instruments") List<Long> instruments, @Param("genres") List<Long> genres, Pageable pageable);

}
