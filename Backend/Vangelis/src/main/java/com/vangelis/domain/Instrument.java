package com.vangelis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


//These Entities will be added manually to the database in order to maintain a control over which instruments are available and to maintain a sense of uniformity
@Entity
@Table(name = "INSTRUMENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instrument
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private Long id;

    @Column(name = "instrument_name", nullable = false)
    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "instrument_icon")
    private byte[] icon;

    public Instrument(String name, byte[] icon) {
        this.name = name;
        this.icon = icon;
    }
}
