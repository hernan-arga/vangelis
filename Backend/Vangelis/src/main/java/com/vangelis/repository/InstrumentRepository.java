package com.vangelis.repository;

import com.vangelis.domain.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long>
{
    @Query(value = "SELECT id from instruments", nativeQuery = true)
    List<Long> getAllIds();
}
