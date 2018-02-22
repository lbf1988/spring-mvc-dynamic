package vip.iten.controller;

import org.springframework.stereotype.Service;

/**
 * UserService
 *
 * @author Brant Liu <br> 邮箱：<br>lbf1988@qq.com <br>日期：<br>2018/2/13
 * @version 1.0.0
 */
@Service("userService")
public class UserService {
    public String toAction(String content){
        return "-->" +  content;
    }
}
