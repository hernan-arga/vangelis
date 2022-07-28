package com.vangelis.repository;

import com.vangelis.domain.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentReporitory extends JpaRepository<Instrument, Long>
{
}
