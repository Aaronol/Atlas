package com.example.atlas.spider.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AjaxParam {
    private String t;
    private String k;
}
