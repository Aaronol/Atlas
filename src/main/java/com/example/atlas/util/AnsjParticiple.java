package com.example.atlas.util;

import com.alibaba.fastjson.JSON;
import com.example.atlas.model.ConditionDto;
import com.example.atlas.model.WordDto;
import com.sun.javafx.util.Utils;
import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.commons.lang3.StringUtils;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;

import java.util.ArrayList;
import java.util.List;

public class AnsjParticiple {
    //加载字典
    private static class Inner {
        static Forest forest;

        static {
            try {
                forest = Library.makeForest(Inner.class.getResourceAsStream("/library/userLibrary.dtc"));
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public String participle(String question) {
        if (StringUtils.isEmpty(question)) {
            return "";
        }
        Result result = ToAnalysis.parse(question, Inner.forest);//分词结果的一个封装，主要是一个List<Term>的terms

        ArrayList<WordDto> words = new ArrayList<>();
        List<Term> terms = result.getTerms(); //拿到terms
        for (Term term : terms) {
            String word = term.getName(); //拿到词
            String natureStr = term.getNatureStr(); //拿到词性
            WordDto wordDto = new WordDto().setWord(word).setWordType(natureStr);
            words.add(wordDto);
        }
        ConditionDto conditionDto = ConditionDto.conditionMatch(words);
        String conditionDtoStr = JSON.toJSONString(conditionDto);
        QueryStrategy.Strategy strategy = null;
        String retData = "";
        //      hasKey;otherKey;nKey;nsKey;mKey;
        if (conditionDtoStr.contains("hasKey") && conditionDtoStr.contains("otherKey") && conditionDtoStr.contains("nKey") && !conditionDtoStr.contains("nsKey") && !conditionDtoStr.contains("mKey")) {
            if (StringUtils.equals(conditionDto.getHasKey().get(0), "作物类型") && StringUtils.equals(conditionDto.getOtherKey().get(0), "有")) {
                strategy = new QueryStrategy.CorpType_O_N();
            }
            if (StringUtils.equals(conditionDto.getHasKey().get(0), "作物") && JSON.toJSONString(conditionDto.getOtherKey()).contains("哪里") && JSON.toJSONString(conditionDto.getOtherKey()).contains("培育")) {
                strategy = new QueryStrategy.Corp_Origin_O_N();
            }
            if (JSON.toJSONString(conditionDto.getHasKey()).contains("系谱") && JSON.toJSONString(conditionDto.getHasKey()).contains("作物") && StringUtils.equals(conditionDto.getOtherKey().get(0), "有")) {
                strategy = new QueryStrategy.Corp_Pedigree_O_M();
            }
            if (JSON.toJSONString(conditionDto.getHasKey()).contains("作物") && StringUtils.equals(conditionDto.getOtherKey().get(0), "介绍")) {
                strategy = new QueryStrategy.Corp_O_N();
            }
            if (JSON.toJSONString(conditionDto.getHasKey()).contains("作物") && StringUtils.equals(conditionDto.getOtherKey().get(0), "什么")) {
                strategy = new QueryStrategy.Corp_O_N();
            }
        }
        if (conditionDtoStr.contains("hasKey") && conditionDtoStr.contains("otherKey") && conditionDtoStr.contains("nsKey") && !conditionDtoStr.contains("nKey") && !conditionDtoStr.contains("mKey")) {
            if (JSON.toJSONString(conditionDto.getHasKey()).contains("产地") && JSON.toJSONString(conditionDto.getHasKey()).contains("作物") && StringUtils.equals(conditionDto.getOtherKey().get(0), "有")) {
                strategy = new QueryStrategy.Corp_Origin_O_NS();
            }
        }
        if (conditionDtoStr.contains("hasKey") && conditionDtoStr.contains("otherKey") && conditionDtoStr.contains("mKey") && !conditionDtoStr.contains("nKey") && !conditionDtoStr.contains("nsKey")) {
            if (JSON.toJSONString(conditionDto.getHasKey()).contains("株高") && JSON.toJSONString(conditionDto.getHasKey()).contains("作物") && StringUtils.equals(conditionDto.getOtherKey().get(0), "有")) {
                strategy = new QueryStrategy.Corp_Height_O_M();
            }
            if (JSON.toJSONString(conditionDto.getHasKey()).contains("千粒重") && JSON.toJSONString(conditionDto.getHasKey()).contains("作物") && StringUtils.equals(conditionDto.getOtherKey().get(0), "有")) {
                strategy = new QueryStrategy.Corp_Weight_O_M();
            }
        }
        if (conditionDtoStr.contains("otherKey") && conditionDtoStr.contains("nKey") && !conditionDtoStr.contains("hasKey") && !conditionDtoStr.contains("nsKey") && !conditionDtoStr.contains("mKey")) {
            if (JSON.toJSONString(conditionDto.getOtherKey()).contains("什么")) {
                strategy = new QueryStrategy.Corp_O_N();
            }
        }

        if (strategy != null) {
            QueryStrategy.Context context = new QueryStrategy.Context();
            context.setStrategy(strategy);
            retData = context.strategyMethod(conditionDto);
        } else {
            retData = "不知道......";
        }
        return retData;
    }
}
