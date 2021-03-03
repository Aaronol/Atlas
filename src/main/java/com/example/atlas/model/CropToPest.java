package com.example.atlas.model;

import lombok.experimental.Accessors;

@Accessors(chain = true)
public class CropToPest {
    private Integer cropid;

    private Integer pestid;

    public Integer getCropid() {
        return cropid;
    }

    public void setCropid(Integer cropid) {
        this.cropid = cropid;
    }

    public Integer getPestid() {
        return pestid;
    }

    public void setPestid(Integer pestid) {
        this.pestid = pestid;
    }
}
