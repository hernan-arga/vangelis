package com.vangelis.doms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class InstrumentListDom
{
    private List<Long> instrumentList;
}
