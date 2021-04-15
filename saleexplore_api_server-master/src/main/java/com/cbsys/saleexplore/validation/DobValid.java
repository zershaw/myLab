package com.cbsys.saleexplore.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {DobValidator.class})

public @interface DobValid {

    String message() default "input date of birth is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
