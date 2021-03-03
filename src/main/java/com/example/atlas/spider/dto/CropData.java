package com.example.atlas.spider.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CropData {
    private String yname;
    private String description;
    private String type1;
    private String type2;
    private String type3;
    private String type4;
    private String name;

    private String thumbs;
    private String id;
}
