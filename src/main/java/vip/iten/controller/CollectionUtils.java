package vip.iten.controller;

import org.springframework.util.StringUtils;

/**
 * CollectionUtils
 *
 * @author Brant Liu <br> 邮箱：<br>lbf1988@qq.com <br>日期：<br>2018/2/13
 * @version 1.0.0
 */
public class CollectionUtils {
    public static boolean contains(String[] source, String condition) {
        if (null == source || (null != source && source.length == 0) || StringUtils.isEmpty(condition)) {
            return false;
        }
        for (String item : source) {
            if (item.equals(condition)) {
                return true;
            }
        }
        return false;
    }
}
