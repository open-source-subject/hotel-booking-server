package com.bookinghotel.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("user")
@Getter
@Setter
public class UserInfoProperties {

    private String email;
    private String phone;
    private String password;
    private String lastName;
    private String firstName;
    private String gender;
    private String birthday;
    private String address;
    private String avatar;
    private String hotline;

}
