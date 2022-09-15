package com.vangelis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;


//These Entities will be added manually to the database in order to maintain a control over which instruments are available and to maintain a sense of uniformity
@Entity
@Table(name = "GENRES")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Genre
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "genre_name", nullable = false)
    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "genre_icon")
    @ToString.Exclude
    private byte[] icon;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Genre genre = (Genre) o;
        return id != null && Objects.equals(id, genre.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
