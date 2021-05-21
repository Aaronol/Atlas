package com.example.atlas.system.routing;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(servletRequest.getParameter("name"));
        HttpServletRequest hrequest = (HttpServletRequest) servletRequest;
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
        System.out.println(JSON.toJSONString(hrequest.getRequestURI()));
        if (StringUtils.equals(hrequest.getRequestURI(), "/login")) {
            wrapper.sendRedirect("/front/login");
        } else if (hrequest.getRequestURI().contains(".jsp") ||
                hrequest.getRequestURI().contains(".js") ||
                hrequest.getRequestURI().contains(".css") ||
                hrequest.getRequestURI().contains(".ico") ||
                hrequest.getRequestURI().contains(".jpg") ||
                hrequest.getRequestURI().contains(".png") ||
                hrequest.getRequestURI().contains(".ttf") ||
                hrequest.getRequestURI().contains(".woff") ||
                hrequest.getRequestURI().contains(".woff2") ||
                hrequest.getRequestURI().contains("/check") ||
                hrequest.getRequestURI().contains("/login")
        ) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (hrequest.getSession(false) != null && hrequest.getSession(false).getAttribute("user") != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                wrapper.sendRedirect("/front/login");
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
