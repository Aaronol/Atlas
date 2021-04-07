package com.example.atlas.model;

public class Crop {
    private Integer id;

    private String croptype;

    private String name;

    private String trainunit;

    private String origin;

    private String remark;

    private String height;

    private String weight;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCroptype() {
        return croptype;
    }

    public void setCroptype(String croptype) {
        this.croptype = croptype == null ? null : croptype.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTrainunit() {
        return trainunit;
    }

    public void setTrainunit(String trainunit) {
        this.trainunit = trainunit == null ? null : trainunit.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height == null ? null : height.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }
}