package com.xb.seckilltest.validator;

import com.xb.seckilltest.annotation.IsMobile;
import com.xb.seckilltest.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * IsMobile  标记注解类型
 * String 注解一般注解的类型，进行校验的数据类
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {

    private boolean required = false;
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        required = constraintAnnotation.required();
    }

    /**
     * 格式判断
     * @param s  数值
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return ValidatorUtil.mobileVerify(s);
        }else {
            if(StringUtils.isEmpty(s)){
                return false;
            }else {
                return ValidatorUtil.mobileVerify(s);
            }
        }
    }
}
