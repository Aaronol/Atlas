package com.example.atlas;

import com.example.atlas.spider.CropPageProcessor;
import com.example.atlas.system.dto.AskResult;
import com.example.atlas.util.AnsjParticiple;
import com.example.atlas.util.DictionaryGeneration;
import com.example.atlas.util.FileWriteUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("/front")
public class HelloController {

    @Resource
    private DictionaryGeneration dictionaryGeneration;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        return "hello";
    }

    @RequestMapping(value = "/creatDic", method = RequestMethod.GET)
    public String creatDic() throws IOException {
        String rootPath = FileWriteUtil.getRootPath() + "\\src\\main\\resources\\library\\userLibrary.dtc";
        this.dictionaryGeneration.generationDictionary(rootPath);
        return "hello";
    }

    @ResponseBody
    @RequestMapping(value = "/participle", method = RequestMethod.GET)
    public String testParticiple(String question) {
        try {
            String decode = URLDecoder.decode(question);
            AnsjParticiple ansjParticiple = new AnsjParticiple();
            return ansjParticiple.participle(decode);
        } catch (Exception e) {
            return "";
        }
    }
}
