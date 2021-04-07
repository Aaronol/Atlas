package com.example.atlas.spider;


import com.alibaba.fastjson.JSON;
import com.example.atlas.model.*;
import com.example.atlas.spider.dto.AjaxParam;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjaxSpiderV2 {
    public void StartSpider(AjaxParam param) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Requested-With", "XMLHttpRequest");
        headers.setContentType(MediaType.APPLICATION_JSON);
//        List<String> cookies =new ArrayList<String>();
//        /* 登录获取Cookie 这里是直接给Cookie，可使用下方的login方法拿到Cookie给入*/
//        cookies.add("XXL_JOB_LOGIN_IDENTITY=6333303830376536353837616465323835626137616465396638383162336437; Path=/; HttpOnly");
//        headers.put(HttpHeaders.COOKIE,cookies);        //将cookie存入头部
        HttpEntity<AjaxParam> reqEntity = new HttpEntity<>(param, headers);
        Map body = restTemplate.exchange("http://crop.agridata.cn/show.asp?%B7%D6%C0%E0=%D3%FD%B3%C9%C6%B7%D6%D6%BC%B0%CF%B5%C6%D7%CA%FD%BE%DD%BF%E2&%D7%F7%CE%EF=%CB%AE%B5%BE", HttpMethod.GET, reqEntity, Map.class).getBody();
        System.out.println(JSON.toJSONString(body));
    }
}
