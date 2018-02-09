package vip.iten.controller;

import org.springframework.beans.factory.InitializingBean;

/**
 * UserController
 *
 * @author Brant Liu <br> 邮箱：<br>lbf1988@qq.com <br>日期：<br>2018/02/08
 * @version 1.0.0
 */
public class UserController implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("我是动态注册的你,不是容器启动的时候注册的你");
    }

    public String toAction(String content){
        return "-->" +  content;
    }
}
