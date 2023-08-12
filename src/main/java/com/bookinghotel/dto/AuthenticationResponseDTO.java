package com.bookinghotel.dto;

import com.bookinghotel.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthenticationResponseDTO {

    private String tokenType = CommonConstant.BEARER_TOKEN;

    private String accessToken;

    private String refreshToken;

    private Collection<? extends GrantedAuthority> authorities;

}
