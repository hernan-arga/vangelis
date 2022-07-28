package com.vangelis.domain;

import javax.persistence.*;

/*
    These Entities will be added manually to the database in order to maintain a control over which instruments are available and to maintain a sense of uniformity
*/
@Entity
@Table(name = "INTRUMENTS")
public class Instrument
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "instrument_name", nullable = false)
    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "instrument_icon")
    private byte[] icon;

    public Instrument() {    }

    public Instrument(String name, byte[] icon) {
        this.name = name;
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }
}
