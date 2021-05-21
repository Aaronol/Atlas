package com.example.atlas;

import com.example.atlas.mapper.UserTableMapper;
import com.example.atlas.model.UserTable;
import com.example.atlas.system.dto.AskResult;
import com.example.atlas.util.AnsjParticiple;
import com.example.atlas.util.DictionaryGeneration;
import com.example.atlas.util.FileWriteUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("/front")
public class HelloController {

    @Resource
    private DictionaryGeneration dictionaryGeneration;

    @Resource
    private UserTableMapper userTableMapper;

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public String index(UserTable userTable, Model model, HttpSession httpSession, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        if (!StringUtils.isEmpty(userTable.getUsername()) && !StringUtils.isEmpty(userTable.getPassword())) {
            List<UserTable> user = userTableMapper.selectByUserName(userTable.getUsername(), userTable.getRoleid());
            if (CollectionUtils.isNotEmpty(user) && StringUtils.equals(user.get(0).getPassword(), userTable.getPassword())) {
                httpSession.setAttribute("user", user.get(0));
                return StringUtils.equals(userTable.getRoleid(), "0") ? "main.jsp" : "user.jsp";
            }
        }
        model.addAttribute("error", "error");
        return "login.jsp";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        return "login.jsp";
    }

    @RequestMapping(value = "/loginout", method = RequestMethod.GET)
    public String loginout(HttpSession httpSession, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        httpSession.removeAttribute("user");
        return "login.jsp";
    }

    @RequestMapping(value = "/query/page", method = RequestMethod.GET)
    public String queryPage(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        return "hello.jsp";
    }

    @ResponseBody
    @RequestMapping(value = "/creatDic", method = RequestMethod.GET)
    public AskResult creatDic() {
        try {
            String rootPath = FileWriteUtil.getRootPath() + "\\src\\main\\resources\\library\\userLibrary.dtc";
            this.dictionaryGeneration.generationDictionary(rootPath);
            return AskResult.success();
        } catch (Exception e) {
            e.getStackTrace();
            return AskResult.failed(e.getMessage());
        }
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

    @ResponseBody
    @RequestMapping(value = "/getAllUser", method = RequestMethod.GET)
    public AskResult getAllUser(String roleid, String username) {
        try {
            return AskResult.success(userTableMapper.selectByRoleId(roleid, username));
        } catch (Exception e) {
            e.getStackTrace();
            return AskResult.failed(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public AskResult deleteUser(String id) {
        try {
            if (StringUtils.isEmpty(id)) {
                userTableMapper.deleteByPrimaryKey(Integer.parseInt(id));
            }
            return AskResult.success();
        } catch (Exception e) {
            e.getStackTrace();
            return AskResult.failed(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/addOrUpdateUser", method = RequestMethod.POST)
    public AskResult addOrUpdateUser(UserTable user) {
        try {
            if (user.getId() != null) {
                userTableMapper.updateByPrimaryKeySelective(user);
            } else {
                userTableMapper.insert(user);
            }
            return AskResult.success();
        } catch (Exception e) {
            e.getStackTrace();
            return AskResult.failed(e.getMessage());
        }
    }
}
