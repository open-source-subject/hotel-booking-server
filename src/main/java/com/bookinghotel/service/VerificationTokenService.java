package com.bookinghotel.service;

import com.bookinghotel.entity.User;
import com.bookinghotel.entity.VerificationToken;

public interface VerificationTokenService {

    VerificationToken getByToken(String token);

    VerificationToken createVerificationToken(User user);

    void deleteToken(Long id);

    //Delete junk token
    void deleteAllJunkVerificationToken();

}
