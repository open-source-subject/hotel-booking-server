package com.bookinghotel.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlConstant {

    public static class User {
        private static final String PRE_FIX = "/user";

        public static final String GET_USERS = PRE_FIX;
        public static final String GET_USER = PRE_FIX + "/{userId}";
        public static final String GET_CURRENT_USER = PRE_FIX + "/current";

        public static final String UPDATE_USER = PRE_FIX + "/update/{userId}";
        public static final String CHANGE_PASS = PRE_FIX + "/change-password";
        public static final String DELETE_USER = PRE_FIX + "/delete/{userId}";
        public static final String LOCK_UNLOCK_USER = PRE_FIX + "/lock-unlock/{userId}";

        private User() {
        }
    }

}
