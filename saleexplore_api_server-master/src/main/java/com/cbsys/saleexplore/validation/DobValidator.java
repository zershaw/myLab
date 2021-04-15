package com.cbsys.saleexplore.validation;

import com.cbsys.saleexplore.util.DateTimeUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;


public class DobValidator implements ConstraintValidator<DobValid, Date> {

    //    "yyyy-MM-dd HH:mm:ss.SSS"
    //    make sure the date is not later than today.
    //    make sure the edge is not over 100 years old.
    @Override
    public boolean isValid(Date inputDate, ConstraintValidatorContext context) {

        int inputAge = DateTimeUtil.computeAge(inputDate);

        return (inputAge >= 0 && inputAge < 100);

    }
}
