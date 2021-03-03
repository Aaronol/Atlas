package com.example.atlas.model;

import lombok.experimental.Accessors;

@Accessors(chain = true)
public class CropToDiseases {
    private Integer cropid;

    private Integer diseasesid;

    public Integer getCropid() {
        return cropid;
    }

    public void setCropid(Integer cropid) {
        this.cropid = cropid;
    }

    public Integer getDiseasesid() {
        return diseasesid;
    }

    public void setDiseasesid(Integer diseasesid) {
        this.diseasesid = diseasesid;
    }
}
