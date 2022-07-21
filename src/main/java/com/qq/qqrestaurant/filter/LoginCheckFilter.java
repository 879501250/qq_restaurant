package com.qq.qqrestaurant.filter;

import com.alibaba.fastjson.JSON;
import com.qq.qqrestaurant.common.BaseContext;
import com.qq.qqrestaurant.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * 检查用户是否登录，未登录则拦截请求
 */
@WebFilter
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    private final static AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
//        log.info("拦截到请求：{}", uri);
//        log.info("拦截到请求：{}", request.getRequestURL());

        // 不需要处理的路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg", // 移动端发送短信
                "/user/login" // 移动端登录
        };
        if (check(urls, uri)) {
            filterChain.doFilter(request, response);
            log.info("拦截到请求：{} 不需要处理", uri);
            return;
        }
        // 已登录
        if (request.getSession().getAttribute("employee") != null) {
            log.info("拦截到请求：{} 已登录", uri);

            // 保存当前登录用户id
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));

            filterChain.doFilter(request, response);

            return;
        }
        // 已登录
        if (request.getSession().getAttribute("user") != null) {
            log.info("拦截到请求：{} 已登录", uri);

            // 保存当前登录用户id
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("user"));

            filterChain.doFilter(request, response);

            return;
        }
        // 未登录，通过输出流方式向客户端页面相应数据，前端检测到 NOTLOGIN 则自动返回登录页
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("拦截到请求：{} 未登录", uri);
        return;
    }

    /**
     * 路径匹配，检查此次请求是否需要放行
     *
     * @param urls
     * @param requestURI
     * @return
     */
    private boolean check(String[] urls, String requestURI) {
        return Arrays.stream(urls).filter(url -> PATH_MATCHER.match(url, requestURI)).count() > 0 ? true : false;
    }
}
