package com.nyzc.gdm.currencyratio.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumerUtil {

    // 判断一个字符串是否含有数字
    public static  boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }
}
