package com.example.atlas.util;

import com.alibaba.fastjson.JSON;
import com.example.atlas.model.ConditionDto;
import com.example.atlas.model.SparqlModel.SparqlDto;
import com.example.atlas.model.SparqlModel.SparqlDtoV2;
import com.example.atlas.model.SparqlModel.XPO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class QueryStrategy {
    //抽象策略类
    interface Strategy {
        String strategyMethod(ConditionDto conditionDto);    //策略方法
    }

    /**
     * -- 作物类型是小麦的有 -n -k 作物类型 -o 有
     */
    public static class CorpType_O_N implements Strategy {
        public String strategyMethod(ConditionDto conditionDto) {
            SparqlDto sparqlDto = RestTemplateUtil.getRestTemplate().sparqlQuery("SELECT * WHERE { ?x :CROP_CROPTYPE '" + conditionDto.getNKey().get(0) + "'. ?x ?p ?o.}");
            StringBuilder stringBuilder = new StringBuilder("有");
            List<XPO> bindings = sparqlDto.getResults().getBindings();
            bindings.forEach(x -> {
                if (x.getP().getValue().contains("#CROP_NAME")) {
                    stringBuilder.append(" ");
                    stringBuilder.append(x.getO().getValue());
                    stringBuilder.append(" ");
                }
            });
            return stringBuilder.toString();
        }
    }

    /**
     * -- 作物京作244是在哪里培育的 -n -k 作物 -o 哪里 培育
     */
    public static class Corp_Origin_O_N implements Strategy {
        public String strategyMethod(ConditionDto conditionDto) {
            SparqlDto sparqlDto = RestTemplateUtil.getRestTemplate().sparqlQuery("SELECT * WHERE {?x :CROP_NAME '" + conditionDto.getNKey().get(0) + "'. ?x ?p ?o. }");
            StringBuilder stringBuilder = new StringBuilder();
            List<XPO> bindings = sparqlDto.getResults().getBindings();
            bindings.forEach(x -> {
                if (x.getP().getValue().contains("#CROP_TRAINUNIT")) {
                    stringBuilder.append(" ");
                    stringBuilder.append(x.getO().getValue());
                    stringBuilder.append(" ");
                }
            });
            return stringBuilder.toString();
        }
    }

    /**
     * -- 产地为广东的作物有 -ns -k 产地 作物 -o 有
     */
    public static class Corp_Origin_O_NS implements Strategy {
        public String strategyMethod(ConditionDto conditionDto) {
            SparqlDto sparqlDto = RestTemplateUtil.getRestTemplate().sparqlQuery("SELECT * WHERE {?x :CROP_ORIGIN '" + conditionDto.getNsKey().get(0) + "'. ?x ?p ?o. }");
            StringBuilder stringBuilder = new StringBuilder("有");
            List<XPO> bindings = sparqlDto.getResults().getBindings();
            bindings.forEach(x -> {
                if (x.getP().getValue().contains("#CROP_NAME")) {
                    stringBuilder.append(" ");
                    stringBuilder.append(x.getO().getValue());
                    stringBuilder.append(" ");
                }
            });
            return stringBuilder.toString();
        }
    }

    /**
     * -- 株高为110的作物有 -m -k 株高 作物 -o 有
     */
    public static class Corp_Height_O_M implements Strategy {
        public String strategyMethod(ConditionDto conditionDto) {
            SparqlDto sparqlDto = RestTemplateUtil.getRestTemplate().sparqlQuery("SELECT * WHERE {?x :CROP_HEIGHT '" + conditionDto.getMKey().get(0) + "'. ?x ?p ?o. }");
            StringBuilder stringBuilder = new StringBuilder("有");
            List<XPO> bindings = sparqlDto.getResults().getBindings();
            bindings.forEach(x -> {
                if (x.getP().getValue().contains("#CROP_NAME")) {
                    stringBuilder.append(" ");
                    stringBuilder.append(x.getO().getValue());
                    stringBuilder.append(" ");
                }
            });
            return stringBuilder.toString();
        }
    }

    /**
     * -- 千粒重为38.0的作物有 -m -k 千粒重 作物 -o 有
     */
    public static class Corp_Weight_O_M implements Strategy {
        public String strategyMethod(ConditionDto conditionDto) {
            SparqlDto sparqlDto = RestTemplateUtil.getRestTemplate().sparqlQuery("SELECT * WHERE {?x :CROP_WEIGHT '" + conditionDto.getMKey().get(0) + "'. ?x ?p ?o. }");
            StringBuilder stringBuilder = new StringBuilder("有");
            List<XPO> bindings = sparqlDto.getResults().getBindings();
            bindings.forEach(x -> {
                if (x.getP().getValue().contains("#CROP_NAME")) {
                    stringBuilder.append(" ");
                    stringBuilder.append(x.getO().getValue());
                    stringBuilder.append(" ");
                }
            });
            return stringBuilder.toString();
        }
    }

    /**
     * -- 系谱为北京7号的作物有 -n -k 系谱 作物 -o 有
     */
    public static class Corp_Pedigree_O_M implements Strategy {
        public String strategyMethod(ConditionDto conditionDto) {
            SparqlDto sparqlDto = RestTemplateUtil.getRestTemplate().sparqlQuery("SELECT * WHERE {?x :PEDIGREE_NAME '" + conditionDto.getNKey().get(0) + "'. ?x ?p ?o. }");
            StringBuilder stringBuilder = new StringBuilder("有");
            List<XPO> bindings = sparqlDto.getResults().getBindings();
            bindings.forEach(x -> {
                if (x.getP().getValue().contains("#parent")) {
                    RestTemplateUtil.getRestTemplate().sparqlQuery("SELECT ?p?o WHERE { { <" + x.getO().getValue() + "> ?p?o } }").getResults().getBindings().forEach(data -> {
                        if (data.getP().getValue().contains("#CROP_NAME")) {
                            stringBuilder.append(" ");
                            stringBuilder.append(data.getO().getValue());
                            stringBuilder.append(" ");
                        }
                    });
                }
            });
            return stringBuilder.toString();
        }
    }

    /**
     * -- 介绍下作物京作244 -n -k 作物 -o 介绍
     * -- 什么是京作244 -n -o 什么
     * -- 什么是作物京作244 -n -k 作物 -o 什么
     */
    public static class Corp_O_N implements Strategy {
        public String strategyMethod(ConditionDto conditionDto) {
            SparqlDto sparqlDto = RestTemplateUtil.getRestTemplate().sparqlQuery("SELECT * WHERE {?x :CROP_NAME '" + conditionDto.getNKey().get(0) + "'. ?x ?p ?o. }");
            StringBuilder stringBuilder = new StringBuilder("");
            ArrayList<String> strings = new ArrayList<>();
            List<XPO> bindings = sparqlDto.getResults().getBindings();
            bindings.forEach(x -> {
                if (x.getP().getValue().contains("#CROP_TRAINUNIT")) {
                    stringBuilder.append("培育地:");
                    stringBuilder.append(x.getO().getValue());
                    stringBuilder.append("\n");
                }
                if (x.getP().getValue().contains("#CROP_NAME")) {
                    stringBuilder.append("作物名:");
                    stringBuilder.append(x.getO().getValue());
                    stringBuilder.append("\n");
                }
                if (x.getP().getValue().contains("#CROP_REMARK")) {
                    stringBuilder.append("备注:");
                    stringBuilder.append(x.getO().getValue());
                    stringBuilder.append("\n");
                }
                if (x.getP().getValue().contains("#CROP_HEIGHT")) {
                    stringBuilder.append("株高:");
                    stringBuilder.append(x.getO().getValue());
                    stringBuilder.append("\n");
                }
                if (x.getP().getValue().contains("#CROP_ORIGIN")) {
                    stringBuilder.append("发源地:");
                    stringBuilder.append(x.getO().getValue());
                    if (StringUtils.isNotEmpty(x.getO().getValue())) {
                        stringBuilder.append("省\n");
                    } else {
                        stringBuilder.append("\n");
                    }
                }
                if (x.getP().getValue().contains("#CROP_WEIGHT")) {
                    stringBuilder.append("千粒重:");
                    stringBuilder.append(x.getO().getValue());
                    stringBuilder.append("\n");
                }
            });
            RestTemplateUtil.getRestTemplate().sparqlQueryV2("SELECT DISTINCT ?property ?hasValue ?isValueOf WHERE { { <" + bindings.get(0).getX().getValue() + "> ?property ?hasValue }UNION{ ?isValueOf ?property <" + bindings.get(0).getX().getValue() + "> } } ORDER BY (!BOUND(?hasValue)) ?property ?hasValue ?isValueOf").getResults().getBindings().forEach(data -> {
                if (data.getProperty().getValue().contains("#parent")) {
                    strings.add(data.getIsValueOf().getValue());
                }
            });
            if (strings.size() > 0) {
                stringBuilder.append("谱系:");
                StringBuilder sparqlQueryV2 = new StringBuilder("SELECT DISTINCT ?property ?hasValue WHERE {");
                for (int i = 0; i < strings.size(); i++) {
                    sparqlQueryV2.append("{ <");
                    sparqlQueryV2.append(strings.get(i));
                    sparqlQueryV2.append("> ?property ?hasValue }");
                    if (i == strings.size() - 1) {
                        sparqlQueryV2.append(" } ");
                    } else {
                        sparqlQueryV2.append(" UNION ");
                    }
                }
                RestTemplateUtil.getRestTemplate().sparqlQueryV2(sparqlQueryV2.toString()).getResults().getBindings().forEach(x -> {
                    if (x.getProperty().getValue().contains("#PEDIGREE_NAME")) {
                        stringBuilder.append(x.getHasValue().getValue());
                        stringBuilder.append("、");
                    }
                });
            }
            return stringBuilder.toString();
        }
    }

    //环境类
    public static class Context {
        private Strategy strategy;

        public Strategy getStrategy() {
            return strategy;
        }

        public void setStrategy(Strategy strategy) {
            this.strategy = strategy;
        }

        public String strategyMethod(ConditionDto conditionDto) {
            return strategy.strategyMethod(conditionDto);
        }
    }
}
