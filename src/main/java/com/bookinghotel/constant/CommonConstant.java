package com.bookinghotel.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonConstant {

    public static final String SORT_TYPE_ASC = "ASC";
    public static final String SORT_TYPE_DESC = "DESC";
    public static final Integer PAGE_SIZE_DEFAULT = 10;
    public static final Integer ZERO_INT_VALUE = 0;
    public static final Integer ONE_INT_VALUE = 1;
    public static final Long ZERO_VALUE = 0L;
    public static final Long ONE_VALUE = 1L;

    public static final Integer DAYS_TO_DELETE_RECORDS = 30;
    public static final Integer HOURS_IN_A_DAY = 24;
    public static final Long LATE_CHECKIN_HOURS = 8L;

    public static final Integer MAX_MESSAGE_CHAT_BOT = 10;

    public static final String EMPTY_STRING = "";
    public static final String BEARER_TOKEN = "Bearer";
    public static final boolean TRUE = true;
    public static final boolean FALSE = false;

    public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE = "yyyy-MM-dd";

    public static final LocalTime TIME_5H00 = LocalTime.of(5, 0);
    public static final LocalTime TIME_9H00 = LocalTime.of(9, 0);
    public static final LocalTime TIME_14H00 = LocalTime.of(14, 0);

    public static final LocalTime TIME_12H00 = LocalTime.of(12, 0);
    public static final LocalTime TIME_15H00 = LocalTime.of(15, 0);
    public static final LocalTime TIME_18H00 = LocalTime.of(18, 0);

}
