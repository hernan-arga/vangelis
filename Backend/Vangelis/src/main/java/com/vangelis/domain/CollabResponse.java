package com.vangelis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "RESPONSES")
@Getter
@Setter
@RequiredArgsConstructor
public class CollabResponse
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.EAGER)
    private Set<Genre> genres;

    @ManyToMany(targetEntity = Instrument.class, fetch = FetchType.EAGER)
    private Set<Instrument> instruments;

    @Column(name = "description", length = 300)
    private String description;

    @ManyToOne
    private User user;

    @ManyToOne
    private MediaObject media;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CollabResponse user = (CollabResponse) o;
        return id != null && Objects.equals(id, user.id);
    }

}
