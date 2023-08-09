package com.bookinghotel.annotation;

import com.bookinghotel.annotation.validator.FileImageValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Constraint(validatedBy = {FileImageValidator.class})
public @interface ValidFileImage {

    String message() default "Only PNG, JPG, WEBP or GIF images are allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
