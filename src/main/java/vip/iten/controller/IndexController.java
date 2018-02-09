package vip.iten.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * IndexController
 *
 * @author Brant Liu <br> 邮箱：<br>lbf1988@qq.com <br>日期：<br>2018/02/08
 * @version 1.0.0
 */
@Controller
public class IndexController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public RequestMappingHandlerMapping requestMappingHandlerMapping;

    @RequestMapping(value="/")
    public String sayHello(ModelMap model){
        model.addAttribute("msg", "Hello,World!");
        return "index";
    }

    @RequestMapping(value="reg")
    public String regBean(ModelMap model){
        // 将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) SpringContextUtil.getApplicationContext();
        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();

        // 通过BeanDefinitionBuilder创建bean定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserController.class);

        // 设置属性userService,此属性引用已经定义的bean:userService,这里userService已经被spring容器管理了.
        //beanDefinitionBuilder.addPropertyReference("userService", "userService");
        // 注册bean
        defaultListableBeanFactory.registerBeanDefinition("userController", beanDefinitionBuilder.getRawBeanDefinition());

        UserController userController = (UserController) SpringContextUtil.getBean("userController");

        model.addAttribute("msg", "register OK" + userController.toAction("动态注册生成调用"));
        System.out.println("context url -----> " + ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/"));
        return "index";
    }

    @RequestMapping(value="mapper")
    public String mapper(String url, ModelMap model){
        if(StringUtils.isEmpty(url)){
            url = "test";
        }

        RequestMappingInfo requestMappingInfo = RequestMappingHandler.getInstance().register(url);

        // 将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) SpringContextUtil.getApplicationContext();
        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();

        // 通过BeanDefinitionBuilder创建bean定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DynamicController.class);
        // 注册bean
        defaultListableBeanFactory.registerBeanDefinition("dynamicController", beanDefinitionBuilder.getRawBeanDefinition());

        Method[] methods = DynamicController.class.getMethods();
        Method defaultMethod = null;
        for(Method method : methods){
            if(method.getName().equalsIgnoreCase("dynamicBind")){
                defaultMethod = method;
            }
        }
        requestMappingHandlerMapping.registerMapping(requestMappingInfo,"dynamicController",defaultMethod);
//        RequestMappingHandler.getInstance().setDetectHandlerMethodsInAncestorContexts(true);
//        RequestMappingHandler.getInstance().afterPropertiesSet();
        model.addAttribute("msg", "Dynamic register["+ url +"] success!!! <br/> <a href=\"/"+ url +"\">Dynamic Test</a>");
        return "index";
    }
}
