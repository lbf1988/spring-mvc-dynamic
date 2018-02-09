package vip.iten.controller;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * IndexController
 *
 * @author Brant Liu <br> 邮箱：<br>lbf1988@qq.com <br>日期：<br>2018/02/08
 * @version 1.0.0
 */
@Controller
public class IndexController {
    @RequestMapping(value="/")
    public String sayHello(ModelMap model){
        model.addAttribute("msg", "Hello,World!");
        return "index";
    }

    @RequestMapping(value="/reg")
    public String regBean(ModelMap model){
//        WebApplicationContextUtils.getWebApplicationContext()
        // 将applicationContext转换为ConfigurableApplicationContext
//        WebApplicationContext
        ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) SpringContextUtil.getApplicationContext();
        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();

        // 通过BeanDefinitionBuilder创建bean定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserController.class);

        // 设置属性userService,此属性引用已经定义的bean:userService,这里userService已经被spring容器管理了.
        //beanDefinitionBuilder.addPropertyReference("userService", "userService");
//        defaultListableBeanFactory.registerSingleton();
        // 注册bean
        defaultListableBeanFactory.registerBeanDefinition("userController", beanDefinitionBuilder.getRawBeanDefinition());

        UserController userController = (UserController) SpringContextUtil.getBean("userController");

        model.addAttribute("msg", "register OK" + userController.toAction("动态注册生成调用"));

        System.out.println("context url -----> " + ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/"));
        return "index";
    }
}
