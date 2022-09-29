package com.vangelis.doms;

import com.vangelis.domain.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class CollaborationDom
{
    private String title;
    private Set<Long> genres;
    private Set<Long> instruments;
    private String description;
    private MediaProvider platform;
    private String mediaUrl;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
