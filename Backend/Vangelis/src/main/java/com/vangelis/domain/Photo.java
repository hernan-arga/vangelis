package com.vangelis.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity @Table(name = "photo_objects")
public class Photo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "user_avatar")
    @ToString.Exclude
    private byte[] image;

    public Photo(byte[] image) {
        this.image = image;
    }
}
