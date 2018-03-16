package vip.iten.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * RequestMappingHandlerHolder
 *
 * @author Brant Liu <br> 邮箱：<br>lbf1988@qq.com <br>日期：<br>2018/03/16
 * @version 1.0.0
 */
public class RequestMappingHandlerHolder extends RequestMappingHandlerMapping {
    protected final Log logger = LogFactory.getLog(getClass());

    private final UrlMappingRegistry urlMappingRegistry = new UrlMappingRegistry();
    /**
     * 获取当前对象
     * @return
     */
    public static RequestMappingHandlerHolder me(){
        return SpringContextUtil.getBean(RequestMappingHandlerHolder.class);
    }

    /**
     * 注册RequestMapping标注类
     * @param clazz
     */
    public void registerHandlerMethods(Class<?> clazz){
        this.registerHandlerMethods(ClassUtils.getShortNameAsProperty(clazz));
    }

    /**
     * 注册RequestMapping标注类
     * @param handler
     */
    public void registerHandlerMethods(Object handler){
        super.detectHandlerMethods(handler);
    }

    @Nullable
    @Override
    public RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
        // TODO: 注册地址到
        if(!this.urlMappingRegistry.register(super.getMappingPathPatterns(info))){
            // TODO: 回滚所有操作...
            throw new IllegalStateException("url["+ getMapping(info) +"] duplication mapping registration");
        }
        return info;
    }

    public String getMapping(RequestMappingInfo info){
        Set<String> mappings = super.getMappingPathPatterns(info);
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<String> iterator = mappings.iterator();
        while (iterator.hasNext()){
            stringBuffer.append(iterator.next());
        }
        return stringBuffer.toString();
    }

    class UrlMappingRegistry{
        private final List<String> registry = new ArrayList<>();

        private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        /**
         * 注册一组地址
         * @param mappers
         */
        public boolean register(Set<String> mappers){
            this.readWriteLock.writeLock().lock();
            try{
                Iterator<String> iterator = mappers.iterator();
                while(iterator.hasNext()){
                    String next = iterator.next();
                    if(!this.registry.contains(next)){
                        this.registry.add(mappers.iterator().next());
                    }else{
                        if(logger.isInfoEnabled()){
                            logger.info("url["+mappers.iterator().next()+"] mapping duplication registration");
                        }
                        return false;
                    }
                }
            }finally {
                this.readWriteLock.writeLock().unlock();
            }
            return true;
        }

        public void unregister(String mapping){
            this.readWriteLock.writeLock().lock();
            try{
                this.registry.remove(mapping);
            }finally {
                this.readWriteLock.writeLock().unlock();
            }
        }
    }
}
