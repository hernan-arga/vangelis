package com.vangelis.domain;

import javax.persistence.*;

@Entity
@Table(name = "SUGGESTIONS")
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

    public Suggestion() {}

    public Suggestion(String type, String name, String comment, Long userId) {
        this.type = type;
        this.name = name;
        this.comment = comment;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
