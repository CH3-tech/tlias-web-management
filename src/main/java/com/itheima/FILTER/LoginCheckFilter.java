package com.itheima.FILTER;

import com.alibaba.fastjson.JSONObject;
import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //获取请求的url
        HttpServletRequest req= (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String url = req.getRequestURI().toString();
        log.info("请求的url：",url);
        if (url.contains("login")){
            log.info("登陆操作，放行");
            filterChain.doFilter(servletRequest, servletResponse);

            return;
        }
        String jwt = req.getHeader("token");
        if (!StringUtils.hasLength(jwt)){

            log.info("请求头token为空，返回未登陆的信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象--json ------>阿里巴巴的fastJSON工具包
            String s = JSONObject.toJSONString(error);
            servletResponse.getWriter().write(s);
            return;
        }
        //解析token，如果解析失败，返回错误结果
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            log.info("解析令牌失败，返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象--json ------>阿里巴巴的fastJSON工具包
            String s = JSONObject.toJSONString(error);
            servletResponse.getWriter().write(s);
            return;
        }

        //放行
        log.info("令牌合法，放行");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
