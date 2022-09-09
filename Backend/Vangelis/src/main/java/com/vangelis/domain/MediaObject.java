package com.vangelis.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity @Table(name = "MEDIA_OBJECTS")
public class MediaObject
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "platform", nullable = false)
    private MediaProvider platform;

    @Column(name = "media_url", nullable = false)
    private String mediaUrl;

    public MediaObject(MediaProvider platform, String mediaUrl) {
        this.platform = platform;
        this.mediaUrl = mediaUrl;
    }
}
