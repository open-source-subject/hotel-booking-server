package com.bookinghotel.job;

import com.bookinghotel.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableScheduling
@ConditionalOnExpression("true")
@RequiredArgsConstructor
public class LockUserRefuseToCheckinJob {

    private final BookingService bookingService;

    @Qualifier("threadPoolTaskExecutorHotelBooking")
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * This job starts at 22:00 PM everyday
     */
    @Scheduled(cron = "0 0/5 22-23 * * *")
    void lockUserRefuseToCheckin() {
        threadPoolTaskExecutor.execute(bookingService::lockUserRefuseToCheckIn);
    }

}
