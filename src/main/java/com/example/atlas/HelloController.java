package com.example.atlas;

import com.example.atlas.mapper.FeedbackMapper;
import com.example.atlas.mapper.UserTableMapper;
import com.example.atlas.model.Feedback;
import com.example.atlas.model.UserTable;
import com.example.atlas.system.dto.AskResult;
import com.example.atlas.util.AnsjParticiple;
import com.example.atlas.util.DictionaryGeneration;
import com.example.atlas.util.FileWriteUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/front")
public class HelloController {

    @Resource
    private DictionaryGeneration dictionaryGeneration;

    @Resource
    private UserTableMapper userTableMapper;

    @Resource
    private FeedbackMapper feedbackMapper;

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

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        return "main.jsp";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        return "user.jsp";
    }

    @RequestMapping(value = "/main/feedback", method = RequestMethod.GET)
    public String feedback_main(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        return "feedback_main.jsp";
    }

    @RequestMapping(value = "/user/feedback", method = RequestMethod.GET)
    public String feedback_user(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        return "feedback_user.jsp";
    }

    @RequestMapping(value = "/personal/center", method = RequestMethod.GET)
    public String personal_center(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        return "personal_center.jsp";
    }

    @RequestMapping(value = "/prepara", method = RequestMethod.GET)
    public String prepara(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        return "prepara.jsp";
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


    /**
     * 用户信息编辑接口
     */
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
    public AskResult deleteUser(@RequestBody UserTable user) {
        try {
            if (user.getId() != null) {
                userTableMapper.deleteByPrimaryKey(user.getId());
            }
            return AskResult.success();
        } catch (Exception e) {
            e.getStackTrace();
            return AskResult.failed(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/addOrUpdateUser", method = RequestMethod.POST)
    public AskResult addOrUpdateUser(@RequestBody UserTable user) {
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

    @ResponseBody
    @RequestMapping(value = "/getUserById", method = RequestMethod.GET)
    public AskResult getUserById(String id) {
        try {
            if (id != null) {
                return AskResult.success(userTableMapper.selectByPrimaryKey(Integer.parseInt(id)));
            }
            return AskResult.success();
        } catch (Exception e) {
            e.getStackTrace();
            return AskResult.failed(e.getMessage());
        }
    }

    /**
     * 反馈模块接口
     */
    @ResponseBody
    @RequestMapping(value = "/getAllFeedBack", method = RequestMethod.GET)
    public AskResult getAllFeedBack(String userid) {
        try {
            Integer num = userid != null ? Integer.parseInt(userid) : null;
            return AskResult.success(feedbackMapper.getAllFeedBack(num));
        } catch (Exception e) {
            e.getStackTrace();
            return AskResult.failed(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveFeedBack", method = RequestMethod.POST)
    public AskResult saveFeedBack(@RequestBody Feedback feedback, HttpSession httpSession) {
        try {
            UserTable user = (UserTable) httpSession.getAttribute("user");
            if (feedback.getId() == null) {
                feedback.setUserid(user.getId());
                feedback.setUsername(user.getUsername());
                feedback.setUpdatedate(new Date());
                feedbackMapper.insert(feedback);
            }else {
                feedbackMapper.updateByPrimaryKeySelective(feedback);
            }
            return AskResult.success();
        } catch (Exception e) {
            e.getStackTrace();
            return AskResult.failed(e.getMessage());
        }
    }
}
