package com.vangelis.repository;

import com.vangelis.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>
{
    @Query(value = "SELECT id from genres", nativeQuery = true)
    List<Long> getAllIds();
}
