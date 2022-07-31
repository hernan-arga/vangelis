package com.vangelis.repository;

import com.vangelis.domain.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentReporitory extends JpaRepository<Instrument, Long>
{
}
