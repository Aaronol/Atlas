package com.example.atlas.model.SparqlModel;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class XPO {
    private XPOAttributes x;
    private XPOAttributes p;
    private XPOAttributes o;
}
