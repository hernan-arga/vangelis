package com.vangelis.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "SUGGESTIONS")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Suggestion
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "suggestion_type", nullable = false)
    private String type;

    @Column(name = "suggestion_name", nullable = false)
    private String name;

    @Column(name = "suggestion_comment")
    private String comment;

    @Column(name = "suggestion_user", nullable = false)
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Suggestion that = (Suggestion) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
