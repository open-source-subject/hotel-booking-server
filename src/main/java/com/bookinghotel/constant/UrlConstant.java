package com.bookinghotel.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlConstant {

    public static class Auth {
        private static final String PRE_FIX = "/auth";

        public static final String LOGIN = PRE_FIX + "/login";

        public static final String SIGNUP = PRE_FIX + "/signup";
        public static final String VERIFY_SIGNUP = SIGNUP + "/verify";

        public static final String FORGOT_PASS = PRE_FIX + "/forgot-password";
        public static final String VERIFY_FORGOT_PASS = FORGOT_PASS + "/verify";

        public static final String LOGOUT = PRE_FIX + "/logout";

        private Auth() {
        }
    }

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

    public static class Room {
        private static final String PRE_FIX = "/room";

        public static final String GET_ROOMS = PRE_FIX;
        public static final String GET_ROOM = PRE_FIX + "/{roomId}";
        public static final String GET_ROOMS_AVAILABLE = PRE_FIX + "/available";
        public static final String GET_ROOM_RATINGS_BY_ROOM = PRE_FIX + "/{roomId}/room-ratings";

        public static final String CREATE_ROOM = PRE_FIX + "/create";
        public static final String UPDATE_ROOM = PRE_FIX + "/update/{roomId}";
        public static final String DELETE_ROOM = PRE_FIX + "/delete/{roomId}";
        public static final String DELETE_ROOM_PERMANENTLY = PRE_FIX + "/delete/trash/{roomId}";
        public static final String RESTORE_ROOM = PRE_FIX + "/restore/{roomId}";

        private Room() {
        }
    }

    public static class RoomRating {
        private static final String PRE_FIX = "/room-rating";

        public static final String GET_ROOM_RATINGS = PRE_FIX;
        public static final String GET_ROOM_RATING = PRE_FIX + "/{roomRatingId}";

        public static final String CREATE_ROOM_RATING = PRE_FIX + "/create/{roomId}";
        public static final String UPDATE_ROOM_RATING = PRE_FIX + "/update/{roomRatingId}";
        public static final String DELETE_ROOM_RATING = PRE_FIX + "/delete/{roomRatingId}";

        private RoomRating() {
        }
    }

    public static class Sale {
        private static final String PRE_FIX = "/sale";

        public static final String GET_SALES = PRE_FIX;
        public static final String GET_SALE = PRE_FIX + "/{saleId}";

        public static final String CREATE_SALE = PRE_FIX + "/create";
        public static final String UPDATE_SALE = PRE_FIX + "/update/{saleId}";
        public static final String DELETE_SALE = PRE_FIX + "/delete/{saleId}";
        public static final String DELETE_SALE_PERMANENTLY = PRE_FIX + "/delete/trash/{saleId}";
        public static final String RESTORE_SALE = PRE_FIX + "/restore/{saleId}";

        public static final String ADD_SALE_TO_ROOM = PRE_FIX + "/add/room/{saleId}";
        public static final String REMOVE_SALE_FROM_ROOMS = PRE_FIX + "/remove/room";

        private Sale() {
        }
    }

    public static class Media {
        private static final String PRE_FIX = "/media";

        public static final String GET_POSTS_IN_TRASH = PRE_FIX + "/trash";
        public static final String DELETE_MEDIA_PERMANENTLY = PRE_FIX + "/delete/trash/{mediaId}";
        public static final String RESTORE_MEDIA = PRE_FIX + "/restore/{mediaId}";

        private Media() {
        }
    }

}
