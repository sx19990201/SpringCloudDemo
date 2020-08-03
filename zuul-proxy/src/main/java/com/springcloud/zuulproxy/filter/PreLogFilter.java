package com.springcloud.zuulproxy.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.protocol.RequestContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PreLogFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(PreLogFilter.class);
    /**
     * 过滤器类型，有pre，routing，post，error四种
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器执行顺序，数值越小优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 是否进行过滤，返回true会执行过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 自定义过滤逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String host= request.getRemoteHost();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        logger.info("Remote host:{},method:{},uri:{}",host,method,uri);
        return null;
    }
}
