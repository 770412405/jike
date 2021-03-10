package org.geektimes.projects.user.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: 肖震
 * @date: 2021/3/10
 * @since:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PwdValidAnnotation.class)
public @interface PwdValid {
    String message() default "{user.validate.pwd}";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    int minLength() default 6;
    int maxLength() default 32;
}
