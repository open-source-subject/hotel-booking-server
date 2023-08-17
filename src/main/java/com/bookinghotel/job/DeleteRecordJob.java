package com.bookinghotel.job;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.service.*;
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
public class DeleteRecordJob {

    private final PostService postService;

    private final RoomService roomService;

    private final MediaService mediaService;

    private final SaleService saleService;

    private final HotelService hotelService;

    private final ProductService productService;

    private final VerificationTokenService verificationTokenService;

    @Qualifier("threadPoolTaskExecutorHotelBooking")
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * This job starts at 3:00 AM everyday
     */
    @Scheduled(cron = "0 0 3 * * *")
    void deleteAllRecordStepOne() {
        threadPoolTaskExecutor.execute(() -> mediaService.deleteMediaByDeleteFlag(CommonConstant.TRUE, CommonConstant.DAYS_TO_DELETE_RECORDS));
        threadPoolTaskExecutor.execute(() -> saleService.deleteSaleByDeleteFlag(CommonConstant.TRUE, CommonConstant.DAYS_TO_DELETE_RECORDS));
        threadPoolTaskExecutor.execute(verificationTokenService::deleteAllJunkVerificationToken);
    }

    /**
     * This job starts at 3:30 AM everyday
     */
    @Scheduled(cron = "0 30 3 * * *")
    void deleteAllRecordStepTwo() {
        threadPoolTaskExecutor.execute(() -> roomService.deleteRoomByDeleteFlag(CommonConstant.TRUE, CommonConstant.DAYS_TO_DELETE_RECORDS));
        threadPoolTaskExecutor.execute(() -> productService.deleteProductByDeleteFlag(CommonConstant.TRUE, CommonConstant.DAYS_TO_DELETE_RECORDS));
        threadPoolTaskExecutor.execute(() -> postService.deletePostByDeleteFlag(CommonConstant.TRUE, CommonConstant.DAYS_TO_DELETE_RECORDS));
    }

    /**
     * This job starts at 4:00 AM everyday
     */
    @Scheduled(cron = "0 0 4 * * *")
    void deleteAllRecordStepThree() {
        threadPoolTaskExecutor.execute(() -> hotelService.deleteServiceByDeleteFlag(CommonConstant.TRUE, CommonConstant.DAYS_TO_DELETE_RECORDS));
    }

}
