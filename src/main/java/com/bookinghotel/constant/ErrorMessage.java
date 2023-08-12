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

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Auth {
        public static final String ERR_INCORRECT_AUTHENTICATION = "Username or password incorrect";
        public static final String ERR_DUPLICATE_EMAIL = "Email is already taken";
        public static final String ERR_DUPLICATE_PASSWORD = "The new password must be different from the old password";
        public static final String ERR_ACCOUNT_NOT_ENABLED = "This account is not enabled";
        public static final String ERR_ACCOUNT_LOCKED = "This account has been locked";

        //error token verification
        public static final String INVALID_TOKEN = "Invalid token";
        public static final String EXPIRED_TOKEN = "Token has expired";
        public static final String INCORRECT_TOKEN = "Incorrect token";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class User {
        public static final String ERR_NOT_FOUND_EMAIL_OR_PHONE = "User not found with this email or phone: %s";
        public static final String ERR_NOT_FOUND_ID = "User not found with id: %s";
        public static final String ERR_ACCOUNT_NOT_FOUND_BY_EMAIL = "User not found with email: %s";
        public static final String ERR_CAN_NOT_UPDATE = "You cannot update other people's information";
        public static final String ERR_CAN_NOT_PERMANENTLY_DELETED = "Non-locked users cannot be permanently deleted";
        public static final String ERR_USER_NOT_ENABLED = "This account is not enabled";
        public static final String ERR_USER_IS_LOCKED = "This account has been locked";
        public static final String ERR_USER_IS_NOT_LOCKED = "This account has not been locked";
        public static final String ERR_OLD_PASSWORD_IS_INCORRECT = "Old password is not incorrect";
        public static final String ERR_NEW_PASSWORD_EQUAL_OLD_PASSWORD = "New password and old password cannot be the same";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Room {
        public static final String ERR_NOT_FOUND_ID = "Room not found with id: %s";
        public static final String ERR_NOT_FOUND_ID_IN_TRASH = "Room not found with id %s in the trash";
        public static final String ERR_NO_PHOTO = "Room needs a photo to describe";
        public static final String ERR_ROOM_UNAVAILABLE = "Sorry, this room is currently unavailable. Please choose another room";
        public static final String ROOM_HAS_BEEN_DISCOUNTED = "Room %s has been discounted";
        public static final String ROOM_NOT_FOR_SALE = "Room %s not for sale %s";
        public static final String ROOM_NO_SALE = "Room %s not for sale";
    }

}
