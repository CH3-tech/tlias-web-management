package com.itheima.FILTER;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


//@WebFilter(urlPatterns = "/*")
public class AbcFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Abc 执行放行前的逻辑");

        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println("Abc 执行放行后的逻辑");
    }
}
