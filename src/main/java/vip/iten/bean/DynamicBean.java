package vip.iten.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import vip.iten.annotation.Param;
import vip.iten.annotation.Test;
import vip.iten.controller.UserService;

import java.io.Serializable;

/**
 * DynamicBean
 *
 * @author Brant Liu <br> 邮箱：<br>lbf1988@qq.com <br>日期：<br>2018/2/13
 * @version 1.0.0
 */
@Test
public class DynamicBean implements Serializable{

    @Autowired
    private UserService userService;

    public String dynamicBind(String name, ModelMap model){
        System.out.println("dynamic param[name] -----> " + userService.toAction("----------------*******"));
        model.put("dynamic","dynamic param[name] -----> "+userService.toAction("----------------*******"));
        return "dynamic";
    }
}
