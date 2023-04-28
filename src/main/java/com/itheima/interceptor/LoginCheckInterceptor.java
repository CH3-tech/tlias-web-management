package com.itheima.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtils;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override//目标资源方法运行前运行，返回true：放行
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        //System.out.println("preHandle..........");

        String url = req.getRequestURI().toString();
        log.info("请求的url：",url);
        if (url.contains("login")){
            log.info("登陆操作，放行");
            return true;
        }
        String jwt = req.getHeader("token");
        if (!StringUtils.hasLength(jwt)){

            log.info("请求头token为空，返回未登陆的信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象--json ------>阿里巴巴的fastJSON工具包
            String s = JSONObject.toJSONString(error);
            resp.getWriter().write(s);
            return false;
        }
        //解析token，如果解析失败，返回错误结果
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            log.info("解析令牌失败，返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象--json ------>阿里巴巴的fastJSON工具包
            String s = JSONObject.toJSONString(error);
            resp.getWriter().write(s);
            return false;
        }

        //放行
        log.info("令牌合法，放行");
        return true;
    }

    @Override//目标资源方法后运行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
       // System.out.println("postHandle........");
    }

    @Override//视图渲染完毕后运行，最后运行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       // System.out.println("afterCompletion.......");
    }
}
