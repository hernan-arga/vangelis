package com.vangelis.doms;

import com.vangelis.domain.MediaProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CollabResponseDom
{
    private String mediaUrl;
    private MediaProvider mediaProvider;
}
