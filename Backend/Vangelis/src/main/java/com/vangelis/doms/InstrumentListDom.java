package com.vangelis.doms;

import java.util.List;

public class InstrumentListDom
{
    private List<Long> instrumentList;

    public InstrumentListDom() {
    }

    public InstrumentListDom(List<Long> instrumentList) {
        this.instrumentList = instrumentList;
    }

    public List<Long> getInstrumentList() {
        return instrumentList;
    }

    public void setInstrumentList(List<Long> instrumentList) {
        this.instrumentList = instrumentList;
    }
}
