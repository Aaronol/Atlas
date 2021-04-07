package com.example.atlas.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class ConditionDto {
    private static List<String> keyWords;
    private static List<String> otherKeyWords;

    private ConditionDto() {
        if (keyWords == null || otherKeyWords == null) {
            String[] keyWordsArrays = {"作物类型", "作物", "培育单位", "培育地", "产地", "生产地", "发源地", "株高", "千粒重", "系谱", "谱系"};
            keyWords = Arrays.asList(keyWordsArrays);
            String[] otherKeyWordsArrays = {"在哪里", "在哪儿", "哪里", "培育", "介绍", "什么", "有"};
            otherKeyWords = Arrays.asList(otherKeyWordsArrays);
        }
    }

    private List<String> hasKey;

    private List<String> otherKey;

    private List<String> nKey;

    private List<String> nsKey;

    private List<String> mKey;

    public static ConditionDto conditionMatch(List<WordDto> words) {
        ConditionDto conditionDto = new ConditionDto();
        words.forEach(x -> {
            if (keyWords.contains(x.getWord())) {
                if (conditionDto.hasKey == null) {
                    conditionDto.hasKey = new ArrayList<>();
                }
                conditionDto.hasKey.add(x.getWord());
                return;
            }
            if (otherKeyWords.contains(x.getWord())) {
                if (conditionDto.otherKey == null) {
                    conditionDto.otherKey = new ArrayList<>();
                }
                conditionDto.otherKey.add(x.getWord());
                return;
            }
            switch (x.getWordType()) {
                case "n":
                    if (conditionDto.nKey == null) {
                        conditionDto.nKey = Collections.singletonList(x.getWord());
                    } else {
                        conditionDto.nKey.add(x.getWordType());
                    }
                    break;
                case "ns":
                    if (conditionDto.nsKey == null) {
                        conditionDto.nsKey = Collections.singletonList(x.getWord());
                    } else {
                        conditionDto.nsKey.add(x.getWordType());
                    }
                    break;
                case "m":
                    if (conditionDto.mKey == null) {
                        conditionDto.mKey = Collections.singletonList(x.getWord());
                    } else {
                        conditionDto.mKey.add(x.getWordType());
                    }
                    break;
            }
        });
        return conditionDto;
    }
}
