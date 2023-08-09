package com.bookinghotel.annotation;

import com.bookinghotel.annotation.validator.FileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Constraint(validatedBy = {FileValidator.class})
public @interface ValidFile {

    String message() default "Only PNG, JPG, WEBP or GIF images and MP4 videos are allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
