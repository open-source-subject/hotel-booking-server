package com.bookinghotel.security;

import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.exception.ForbiddenException;
import com.bookinghotel.exception.UnauthorizedException;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

@Aspect
@Configuration
@Log4j2
public class CheckAuthorizationAspect {

    @Before("@annotation(authorizationInfo)")
    public void before(JoinPoint joinPoint, AuthorizationInfo authorizationInfo) {
        log.info("Before called " + joinPoint.toString());
        boolean isValid = false;
        UserPrincipal userPrincipal;
        List<String> roles = Arrays.asList(authorizationInfo.role());
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userPrincipal = (UserPrincipal) authentication.getPrincipal();
        } catch (Exception ex) {
            throw new UnauthorizedException(ErrorMessage.UNAUTHORIZED);
        }

        if (userPrincipal.getAuthorities() != null) {
            for (GrantedAuthority authority : userPrincipal.getAuthorities()) {
                if (roles.contains(authority.getAuthority())) {
                    isValid = true;
                    break;
                }
            }
        }
        if (!isValid) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }
    }
}
