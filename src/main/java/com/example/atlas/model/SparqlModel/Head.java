package com.example.atlas.model.SparqlModel;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Head {
    private List<String> vars;
}
