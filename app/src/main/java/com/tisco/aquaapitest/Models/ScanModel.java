package com.tisco.aquaapitest.Models;

public class ScanModel {
    Integer id, key_id, numpass, numwarn, numfail,numunknown, numnewrisk;
    String created,name;

    public ScanModel(Integer id, Integer key_id, Integer numpass, Integer numwarn, Integer numfail, Integer numunknown, Integer numnewrisk, String created, String name) {
        this.id = id;
        this.key_id = key_id;
        this.numpass = numpass;
        this.numwarn = numwarn;
        this.numfail = numfail;
        this.numunknown = numunknown;
        this.numnewrisk = numnewrisk;
        this.created = created;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public Integer getKey_id() {
        return key_id;
    }

    public Integer getNumpass() {
        return numpass;
    }

    public Integer getNumwarn() {
        return numwarn;
    }

    public Integer getNumfail() {
        return numfail;
    }

    public Integer getNumunknown() {
        return numunknown;
    }

    public Integer getNumnewrisk() {
        return numnewrisk;
    }

    public String getCreated() {
        return created;
    }

    public String getName() {
        return name;
    }
}
