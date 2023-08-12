package com.bookinghotel.constant;

public enum SortByDataConstant implements SortByInterface {

    USER {
        @Override
        public String getSortBy(String sortBy) {
            switch (sortBy) {
                case "firstName":
                    return "first_Name";
                case "lastName":
                    return "last_name";
                case "gender":
                    return "gender";
                case "birthday":
                    return "birthday";
                case "lastModifiedDate":
                    return "last_modified_date";
                default:
                    return "created_date";
            }
        }
    },
    SALE {
        @Override
        public String getSortBy(String sortBy) {
            switch (sortBy) {
                case "dayStart":
                    return "day_start";
                case "dayEnd":
                    return "day_end";
                case "salePercent":
                    return "sale_percent";
                case "lastModifiedDate":
                    return "last_modified_date";
                default:
                    return "created_date";
            }
        }
    },
    ROOM {
        @Override
        public String getSortBy(String sortBy) {
            switch (sortBy) {
                case "title":
                    return "title";
                case "price":
                    return "price";
                case "maxNum":
                    return "max_num";
                case "floor":
                    return "floor";
                case "lastModifiedDate":
                    return "last_modified_date";
                default:
                    return "created_date";
            }
        }
    },
    ROOM_RATING {
        @Override
        public String getSortBy(String sortBy) {
            switch (sortBy) {
                case "star":
                    return "star";
                case "lastModifiedDate":
                    return "last_modified_date";
                default:
                    return "created_date";
            }
        }
    },
    POST {
        @Override
        public String getSortBy(String sortBy) {
            switch (sortBy) {
                case "lastModifiedDate":
                    return "last_modified_date";
                default:
                    return "created_date";
            }
        }
    },
    SERVICE {
        @Override
        public String getSortBy(String sortBy) {
            switch (sortBy) {
                case "title":
                    return "title";
                case "price":
                    return "price";
                case "lastModifiedDate":
                    return "last_modified_date";
                default:
                    return "created_date";
            }
        }
    },
    PRODUCT {
        @Override
        public String getSortBy(String sortBy) {
            switch (sortBy) {
                case "name":
                    return "name";
                case "lastModifiedDate":
                    return "last_modified_date";
                default:
                    return "created_date";
            }
        }
    },
    BOOKING {
        @Override
        public String getSortBy(String sortBy) {
            switch (sortBy) {
                case "checkIn":
                    return "check_in";
                case "checkOut":
                    return "check_out";
                case "lastModifiedDate":
                    return "last_modified_date";
                default:
                    return "created_date";
            }
        }
    },

}
