package org.geektimes.projects.user.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


import java.util.regex.Pattern;

/**
 * @author: 肖震
 * @date: 2021/3/10
 * @since:
 */

public class PhoneValidAnnotation implements ConstraintValidator<PhoneValid, String> {
    @Override
    public void initialize(PhoneValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if(phone==null||!Pattern.matches("^1[3-9]\\d{9}$", phone)){
            return false;
        }
        return true;
    }

}
