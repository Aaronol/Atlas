package com.example.atlas.spider;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import us.codecraft.webmagic.Spider;

@Controller
@RequestMapping("/magic")
public class WebMagicController {

    @RequestMapping(value = "/spider", method = RequestMethod.GET)
    public String login() {
        Spider.create(new CropPageProcessor()).addUrl("http://cloud.sinoverse.cn/index_bch-list.html").thread(5).run();
        return "";
    }
}
