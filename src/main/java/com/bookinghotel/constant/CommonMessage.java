package com.bookinghotel.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonMessage {

    public static final String SIGNUP_SUCCESS = "SignUp successfully";
    public static final String FORGOT_PASSWORD_SUCCESS = "Change password successfully";
    public static final String UPDATE_SUCCESS = "Update successfully";
    public static final String ADD_SUCCESS = "Add successfully";
    public static final String DELETE_SUCCESS = "Delete successfully";
    public static final String RESTORE_SUCCESS = "Restore successfully";
    public static final String LOCK_SUCCESS = "Lock successfully";
    public static final String UNLOCK_SUCCESS = "Unlock successfully";
    public static final String CANCEL_SUCCESS = "Cancel successfully";
    public static final String CHANGE_PASSWORD_SUCCESS = "Change password successfully";

    //Mail
    public static final String SUBJECT_REGISTER = "Confirm account registration";
    public static final String SIGNUP_TEMPLATE = "SignUp.html";
    public static final String SUBJECT_FORGOT_PASS = "Forgot password";
    public static final String FORGOT_PASSWORD_TEMPLATE = "ForgotPassword.html";
    public static final String SUBJECT_ACCOUNT_LOCK_NOTICE = "Account is locked";
    public static final String ACCOUNT_LOCK_NOTICE_TEMPLATE = "AccountLockNotice.html";

}
