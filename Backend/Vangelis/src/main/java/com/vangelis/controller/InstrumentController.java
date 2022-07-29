package com.vangelis.controller;

import com.vangelis.domain.Instrument;
import com.vangelis.repository.InstrumentReporitory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/instruments")
public class InstrumentController
{
    final InstrumentReporitory instrumentReporitory;

    public InstrumentController(InstrumentReporitory instrumentReporitory)
    {
        this.instrumentReporitory = instrumentReporitory;
    }

    @GetMapping
    public ResponseEntity<List<Instrument>> getAll()
    {
        try
        {
            List<Instrument> instruments = instrumentReporitory.findAll();
            return new ResponseEntity(instruments, HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instrument> getOne(@PathVariable Long id)
    {
        try
        {
            Instrument instrument = instrumentReporitory.findById(id).orElseThrow();
            return new ResponseEntity(instrument, HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }
}
