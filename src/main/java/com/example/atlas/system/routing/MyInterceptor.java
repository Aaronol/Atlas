package com.example.atlas.system.routing;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Map map = (Map) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        System.out.println(httpServletRequest.getRequestURI());
        System.out.println(httpServletRequest.getQueryString ());
        System.out.println(httpServletRequest.getRemoteAddr ());
        System.out.println(httpServletRequest.getRemoteHost ());
        System.out.println(httpServletRequest.getRemotePort ());
        System.out.println(httpServletRequest.getLocalAddr ());
        System.out.println(httpServletRequest.getLocalName ());
        System.out.println(map.get("name"));
        System.out.println(httpServletRequest.getParameter("username"));
        if (map.get("name").equals("zhangsan")) {
            return true;    //如果false，停止流程，api被拦截
        } else {
            PrintWriter printWriter = httpServletResponse.getWriter();
            printWriter.write("please login again!");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle被调用");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion被调用");
    }
}
