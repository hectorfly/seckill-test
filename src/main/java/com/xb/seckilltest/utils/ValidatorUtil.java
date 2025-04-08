package com.xb.seckilltest.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class ValidatorUtil {

    private static final Pattern mobile_pattern = Pattern.compile("[1]([3-9])[0-9]{9}$");

    public static boolean mobileVerify(String mobile){

        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        return mobile_pattern.matcher(mobile).matches();
    }
}
