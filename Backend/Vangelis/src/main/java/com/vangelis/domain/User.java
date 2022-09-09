package com.vangelis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.util.Pair;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@RequiredArgsConstructor
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @JsonIgnore
    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_email", nullable = false)
    private String email;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "user_avatar")
    @ToString.Exclude
    private byte[] userAvatar;

    @Column(name = "user_bio", length = 300)
    private String bio;

    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.EAGER)
    private Set<Genre> favoriteGenres;

    @ManyToMany(targetEntity = Instrument.class, fetch = FetchType.EAGER)
    private Set<Instrument> instruments;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<MediaObject> videos;

    public User(String userName, String encodedPassword, String email)
    {
        this.userName = userName;
        this.password = encodedPassword;
        this.email = email;
    }

    public void addVideos(List<MediaObject> videos)
    {
        this.videos.addAll(videos);
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public String toString() {
        return "{id=" + this.id + ", name='" + this.userName + "', email='" + this.email + "'" + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
