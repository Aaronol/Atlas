package com.example.atlas.model.SparqlModel;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SparqlDto {
    private Head head;
    private Bindings results;
}
