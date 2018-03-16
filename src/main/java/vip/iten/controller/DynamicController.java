package vip.iten.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * DynamicController
 *
 * @author Brant Liu <br> 邮箱：<br>lbf1988@qq.com <br>日期：<br>2018/2/9
 * @version 1.0.0
 */
@RequestMapping(value = "dynamic")
public class DynamicController{
    @RequestMapping(value = "dynamicTest")
    public String dynamicBind(String name, ModelMap model){
        System.out.println("dynamic param[name] -----> " + name);
        model.put("dynamic","dynamic param[name] -----> "+name);
        return "dynamic";
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("我是动态注册的你,不是容器启动的时候注册的你");
//    }
}
