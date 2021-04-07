package com.example.atlas.util;

import com.alibaba.fastjson.JSON;
import com.example.atlas.model.SparqlModel.SparqlDto;
import com.example.atlas.model.SparqlModel.SparqlDtoV2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class RestTemplateUtil {
    private RestTemplate restTemplate;
    private static RestTemplateUtil restTemplateUtil = null;

    private RestTemplateUtil() {
        this.restTemplate = new RestTemplate();
    }

    public static RestTemplateUtil getRestTemplate() {
        if (restTemplateUtil == null) {
            restTemplateUtil = new RestTemplateUtil();
        }
        return restTemplateUtil;
    }

    public SparqlDto sparqlQuery(String sparql) {
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
                "PREFIX db: <http://localhost:2020/resource/>\n" + sparql);
        HttpEntity reqEntity = new HttpEntity(bodyParam, headers);
        Map<String, String> httpResult = this.restTemplate.exchange("http://localhost:2020/sparql", HttpMethod.POST, reqEntity, Map.class).getBody();

        System.out.println(JSON.toJSONString(httpResult));
        SparqlDto sparqlDto = JSON.parseObject(JSON.toJSONString(httpResult), SparqlDto.class);
        return sparqlDto;
    }

    public SparqlDtoV2 sparqlQueryV2(String sparql) {
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
                "PREFIX db: <http://localhost:2020/resource/>\n" + sparql);
        HttpEntity reqEntity = new HttpEntity(bodyParam, headers);
        Map<String, String> httpResult = this.restTemplate.exchange("http://localhost:2020/sparql", HttpMethod.POST, reqEntity, Map.class).getBody();

        System.out.println(JSON.toJSONString(httpResult));
        SparqlDtoV2 sparqlDto = JSON.parseObject(JSON.toJSONString(httpResult), SparqlDtoV2.class);
        return sparqlDto;
    }
}
