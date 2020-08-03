package com.springcloud.hystrixservice.filter;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.File;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class HystrixRequestContextFilter implements Filter {

    /**
     * 为Hystrix添加缓存必须需要过滤一次，初始上下文
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HystrixRequestContext context =HystrixRequestContext.initializeContext();
        try{
            filterChain.doFilter(servletRequest,servletResponse);
        }finally {
            context.close();
        }

    }
}
