package com.example.atlas.model.SparqlModel;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class BindingsV2 {
    List<BindingsDatas> bindings;
}
