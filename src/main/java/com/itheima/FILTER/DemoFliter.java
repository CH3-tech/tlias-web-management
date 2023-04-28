package com.itheima.FILTER;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.logging.LogRecord;



//@WebFilter(urlPatterns = "/*")
//@WebFilter(urlPatterns = "/*")
public class DemoFliter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init初始化方法执行了");
    }

    @Override
    public void destroy() {
        System.out.println("destroy销毁方法执行了");
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //拦截到请求之后调用，会调用多次
        System.out.println("Demo 拦截了请求...放行前逻辑");
        //放行
        filterChain.doFilter(servletRequest,servletResponse);

        System.out.println("Demo 拦截了请求...放行后逻辑");
    }

}
