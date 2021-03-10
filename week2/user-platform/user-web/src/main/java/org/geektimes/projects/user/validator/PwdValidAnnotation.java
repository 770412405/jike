package org.geektimes.projects.user.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author: 肖震
 * @date: 2021/3/10
 * @since:
 */
public class PwdValidAnnotation implements ConstraintValidator<PwdValid, String> {
    private int minLength;
    private int maxLength;

    @Override
    public void initialize(PwdValid constraintAnnotation) {
        this.minLength=constraintAnnotation.minLength();
        this.maxLength=constraintAnnotation.maxLength();
    }

    @Override
    public boolean isValid(String pwd, ConstraintValidatorContext constraintValidatorContext) {
        if(pwd==null||pwd.length()<minLength||pwd.length()>maxLength){
            return false;
        }
        return true;
    }

}
