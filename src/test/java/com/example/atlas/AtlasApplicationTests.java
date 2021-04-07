package com.example.atlas;

import com.alibaba.fastjson.JSON;
import com.example.atlas.model.ConditionDto;
import com.example.atlas.model.SparqlModel.SparqlDto;
import com.example.atlas.model.WordDto;
import com.example.atlas.util.AnsjParticiple;
import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.SegmentationAlgorithm;

import org.apdplat.word.segmentation.Word;
import org.apdplat.word.tagging.PartOfSpeechTagging;
import org.apdplat.word.tagging.SynonymTagging;
import org.junit.jupiter.api.Test;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
class AtlasApplicationTests {

    private static class Inner {
        static Forest forest;

        static {
            try {
                forest = Library.makeForest(AtlasApplicationTests.Inner.class.getResourceAsStream("/library/userLibrary.dtc"));
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    @Test
    void contextLoads() {
        List<Word> word1 = WordSegmenter.seg("介绍下京作244");
//        List<WordDto> word2 = WordSegmenter.seg("危害豇豆的虫害有哪些");
//        List<WordDto> word3 = WordSegmenter.seg("危害玉米的病害有哪些");
//        List<WordDto> word4 = WordSegmenter.seg("甜菜夜蛾危害哪些农作物");
//        List<WordDto> word5 = WordSegmenter.seg("水稻黄萎病危害哪些农作物");
        System.out.println("---------------------------------------------------------");
        PartOfSpeechTagging.process(word1);
//        PartOfSpeechTagging.process(word2);
//        PartOfSpeechTagging.process(word3);
//        PartOfSpeechTagging.process(word4);
//        PartOfSpeechTagging.process(word5);
        System.out.println(JSON.toJSONString(word1));
//        System.out.println(JSON.toJSONString(word2));
//        System.out.println(JSON.toJSONString(word3));
//        System.out.println(JSON.toJSONString(word4));
//        System.out.println(JSON.toJSONString(word5));
        System.out.println("---------------------------------------------------------");
    }

    @Test
    void contextLoadTwos() {
        List<Word> word1 = WordSegmenter.seg("危害哪些农作物", SegmentationAlgorithm.MaximumMatching);
        List<Word> words = new ArrayList<>();
        word1.forEach(x -> {
            List<Word> zWords = new ArrayList<>();
            zWords.add(x);
            PartOfSpeechTagging.process(zWords);
            words.addAll(zWords);
        });
        System.out.println(JSON.toJSONString(words));
    }

    @Test
    void contextLoadThree() {
        String str = "株高为110的作物有";
        Result result = ToAnalysis.parse(str, AtlasApplicationTests.Inner.forest);//分词结果的一个封装，主要是一个List<Term>的terms

        KeyWordComputer keyWordComputer = new KeyWordComputer(10);
//        List list = keyWordComputer.computeArticleTfidf(str);
//        System.out.println(JSON.toJSONString(list));
        ArrayList<WordDto> words = new ArrayList<>();
        List<Term> terms = result.getTerms(); //拿到terms
        for (int i = 0; i < terms.size(); i++) {
            String word = terms.get(i).getName(); //拿到词
            String natureStr = terms.get(i).getNatureStr(); //拿到词性
            WordDto word1 = new WordDto().setWord(word).setWordType(natureStr);
            words.add(word1);
        }
        System.out.println(JSON.toJSONString(words));
        System.out.println(JSON.toJSONString(ConditionDto.conditionMatch(words)));
    }


    @Test
    void getPath() throws IOException {
        File directory = new File("");//参数为空
        String courseFile = directory.getCanonicalPath();//标准的路径 ;
        String author = directory.getAbsolutePath();//绝对路径;
        System.out.println(author);
//        System.out.println(Objects.requireNonNull(AtlasApplicationTests.class.getClassLoader().getResource("application.properties")).getPath());
    }

    @Test
    void postRequest() {
        RestTemplate proxyRestTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, Object> bodyParam = new LinkedMultiValueMap<>();
        bodyParam.add("output", "json");
        bodyParam.add("query", "PREFIX : <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX map: <http://localhost:2020/resource/#>\n" +
                "PREFIX db: <http://localhost:2020/resource/>\n" +
                "SELECT * WHERE {\n" +
                "?x :CROP_NAME '京作244'.\n" +
                "?x ?p ?o.\n" +
                "}");

        HttpEntity reqEntity = new HttpEntity(bodyParam, headers);
        Map<String, String> httpResult = proxyRestTemplate.exchange("http://localhost:2020/sparql", HttpMethod.POST, reqEntity, Map.class).getBody();

        System.out.println(JSON.toJSONString(httpResult));
        SparqlDto sparqlDto = JSON.parseObject(JSON.toJSONString(httpResult), SparqlDto.class);
        System.out.println(JSON.toJSONString(sparqlDto));
    }


}
