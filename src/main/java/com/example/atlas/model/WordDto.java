package com.example.atlas.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WordDto {
    private String word;
    private String wordType;
}
