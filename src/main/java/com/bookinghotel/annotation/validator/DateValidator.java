package com.bookinghotel.annotation.validator;

import com.bookinghotel.annotation.DateValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidator implements ConstraintValidator<DateValid, String> {

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            LocalDate.parse(String.format(date, dtf), dtf);
        } catch (DateTimeException dte) {
            return false;
        }
        return true;
    }
}
