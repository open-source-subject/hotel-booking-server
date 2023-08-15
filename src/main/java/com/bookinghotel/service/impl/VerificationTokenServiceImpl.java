package com.bookinghotel.service.impl;

import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.entity.User;
import com.bookinghotel.entity.VerificationToken;
import com.bookinghotel.exception.InvalidException;
import com.bookinghotel.repository.VerificationTokenRepository;
import com.bookinghotel.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    @Qualifier("threadPoolTaskExecutorHotelBooking")
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public VerificationToken getByToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        checkVerificationToken(verificationToken);
        return verificationToken;
    }

    @Override
    public VerificationToken createVerificationToken(User user) {
        VerificationToken verificationTokenCurrent = verificationTokenRepository.findByUserEmail(user.getEmail());
        UUID token = UUID.randomUUID();
        if (ObjectUtils.isEmpty(verificationTokenCurrent)) {
            VerificationToken verificationToken = new VerificationToken(user, token.toString());
            return verificationTokenRepository.save(verificationToken);
        } else {
            verificationTokenCurrent.setToken(token.toString());
            verificationTokenCurrent.setExpirationTime(verificationTokenCurrent.calculateExpirationDate());
            verificationTokenRepository.save(verificationTokenCurrent);
            return verificationTokenCurrent;
        }
    }

    @Override
    public void deleteToken(Long id) {
        verificationTokenRepository.deleteById(id);
    }

    @Override
    public void deleteAllJunkVerificationToken() {
        List<VerificationToken> verificationTokenList = verificationTokenRepository.findAll();
        Calendar cal = Calendar.getInstance();
        for (VerificationToken verificationToken : verificationTokenList) {
            if ((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
                threadPoolTaskExecutor.execute(() -> verificationTokenRepository.delete(verificationToken));
            }
        }
    }

    private void checkVerificationToken(VerificationToken verificationToken) {
        if (ObjectUtils.isEmpty(verificationToken)) {
            throw new InvalidException(ErrorMessage.Auth.INVALID_TOKEN);
        } else {
            Calendar cal = Calendar.getInstance();
            if ((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
                throw new InvalidException(ErrorMessage.Auth.EXPIRED_TOKEN);
            }
        }
    }

}
