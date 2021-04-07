package com.example.atlas.model.SparqlModel;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BindingsDatas {
    private XPOAttributes property;
    private XPOAttributes hasValue;
    private XPOAttributes isValueOf;
}
