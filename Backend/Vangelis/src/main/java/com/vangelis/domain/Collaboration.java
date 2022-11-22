package com.vangelis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vangelis.doms.CollabResponseDom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "COLLABS")
@Getter
@Setter
@RequiredArgsConstructor
public class Collaboration
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.EAGER)
    private Set<Genre> genres;

    @ManyToMany(targetEntity = Instrument.class, fetch = FetchType.EAGER)
    private Set<Instrument> instruments;

    @Column(name = "description", length = 300)
    private String description;

    @Column(nullable = true)
    private boolean isOpen = true;

    @ManyToOne
    private User user;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<CollabResponse> responses;

    @ManyToOne
    private MediaObject media;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Collaboration user = (Collaboration) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Collaboration(String title, Set<Genre> genres, Set<Instrument> instruments, String description, User user, MediaObject media) {
        this.title = title;
        this.genres = genres;
        this.instruments = instruments;
        this.description = description;
        this.user = user;
        this.media = media;
        this.isOpen = true;
    }

    public void addResponse(CollabResponse response)
    {
        this.responses.add(response);
    }
}
