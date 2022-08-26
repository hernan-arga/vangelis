package com.vangelis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "SUGGESTIONS")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
