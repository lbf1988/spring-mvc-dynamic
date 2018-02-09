package vip.iten.controller;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * RequestMappingHandler
 *
 * @author Brant Liu <br> 邮箱：<br>lbf1988@qq.com <br>日期：<br>2018/2/9
 * @version 1.0.0
 */
public class RequestMappingHandler extends RequestMappingHandlerMapping {

    private RequestMappingInfo.BuilderConfiguration config;

    private static class LazyHolder {
        private static final RequestMappingHandler INSTANCE = new RequestMappingHandler();
    }

    private RequestMappingHandler() {
        this.config = new RequestMappingInfo.BuilderConfiguration();
        this.config.setUrlPathHelper(getUrlPathHelper());
        this.config.setPathMatcher(getPathMatcher());
        this.config.setSuffixPatternMatch(super.useSuffixPatternMatch());
        this.config.setTrailingSlashMatch(super.useTrailingSlashMatch());
        this.config.setRegisteredSuffixPatternMatch(super.useRegisteredSuffixPatternMatch());
        this.config.setContentNegotiationManager(getContentNegotiationManager());
    }

    public static final RequestMappingHandler getInstance() {
        return LazyHolder.INSTANCE;
    }

    public RequestMappingInfo register(String url){
        RequestMappingInfo.Builder builder = RequestMappingInfo.paths(url)
                .methods(RequestMethod.GET);
        return builder.options(this.config).build();
    }
}
