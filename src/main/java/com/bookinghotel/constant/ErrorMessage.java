package com.bookinghotel.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessage {

    public static final String UNAUTHORIZED = "Sorry, you needs to provide authentication credentials to access this resource";
    public static final String FORBIDDEN = "Sorry, you do not have the necessary permissions to access this resource";
    public static final String FORBIDDEN_UD = "You do not have permission to edit or delete this resource";
    public static final String ERR_EXCEPTION_GENERAL = "Something went wrong, please try again later";

    //error validation dto
    public static final String INVALID_SOME_THING_FIELD_IS_REQUIRED = "This field is required";
    public static final String INVALID_SOME_THING_FIELD = "Invalid data";
    public static final String INVALID_FORMAT_PASSWORD = "Unsatisfactory password";
    public static final String NOT_BLANK_FIELD = "Can't be blank";
    public static final String ERR_INVALID_FILE = "Invalid file format";
    public static final String INVALID_DATE = "Invalid time";
    public static final String INVALID_DATE_CHECK_IN = "Invalid check-in date";
    public static final String INVALID_DATE_CHECK_OUT = "Invalid check-out date";
    public static final String INVALID_FORMAT_SOME_THING_FIELD = "Invalid format";

}
