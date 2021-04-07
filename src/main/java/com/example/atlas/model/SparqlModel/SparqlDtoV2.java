package com.example.atlas.model.SparqlModel;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SparqlDtoV2 {
    private Head head;
    private BindingsV2 results;
}
