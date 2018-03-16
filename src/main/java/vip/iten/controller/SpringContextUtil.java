package vip.iten.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * SpringContextUtil
 *
 * @author Brant Liu <br> 邮箱：<br>lbf1988@qq.com <br>日期：<br>2018/02/08
 * @version 1.0.0
 */
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 获取Bean
     * @param name spring bean name
     * @return bean
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException{
        try{
            return applicationContext.getBean(name);
        }catch (Exception ex){
            throw new RuntimeException("get spring bean is not exist! please check bean["+name+"] is initialized");
        }
    }

    /**
     * 获取Bean
     * @param name spring bean name
     * @param requiredType spring bean class type
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(String name, Class<T> requiredType)
            throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    /**
     * 获取Bean
     * @param requiredType
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> requiredType) throws BeansException{
        return applicationContext.getBean(requiredType);
    }

    /**
     * 检查是否存在Bean
     * @param name
     * @return
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    /**
     * 检查是否单例模式
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }

    /**
     * 获取Bean的类型
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    public static Class<? extends Object> getType(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getType(name);
    }

    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getAliases(name);
    }

    public static ConfigurableApplicationContext getConfigurableApplicationContext(){
        return (ConfigurableApplicationContext) getApplicationContext();
    }
}
