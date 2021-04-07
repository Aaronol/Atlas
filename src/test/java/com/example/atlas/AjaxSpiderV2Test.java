package com.example.atlas;

import com.alibaba.fastjson.JSON;
import com.example.atlas.spider.dto.AjaxParam;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class AjaxSpiderV2Test {

    public class WxMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        public WxMappingJackson2HttpMessageConverter(){
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_PLAIN);
            mediaTypes.add(MediaType.TEXT_HTML);  //加入text/html类型的支持
            setSupportedMediaTypes(mediaTypes);
        }
    }

    @Test
    public void StartSpider() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        List<String> cookies =new ArrayList<String>();
        /* 登录获取Cookie 这里是直接给Cookie，可使用下方的login方法拿到Cookie给入*/
        cookies.add("ASPSESSIONIDCATSBRBR=CMBCOPHBOBPGJMJMIBCLGOGC; Path=/;");
        headers.put(HttpHeaders.COOKIE,cookies);        //将cookie存入头部
        HttpEntity<AjaxParam> reqEntity = new HttpEntity<>(headers);
        restTemplate.exchange("http://crop.agridata.cn/show.asp?%B7%D6%C0%E0=%D3%FD%B3%C9%C6%B7%D6%D6%BC%B0%CF%B5%C6%D7%CA%FD%BE%DD%BF%E2&%D7%F7%CE%EF=%CB%AE%B5%BE", HttpMethod.GET, reqEntity, Map.class).getBody();
        System.out.println(JSON.toJSONString(""));
    }
}
