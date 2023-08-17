package com.bookinghotel;

import com.bookinghotel.config.UserInfoProperties;
import com.bookinghotel.constant.RoleConstant;
import com.bookinghotel.dto.init.RoomInitJSON;
import com.bookinghotel.dto.init.ServiceInitJSON;
import com.bookinghotel.entity.*;
import com.bookinghotel.mapper.RoomMapper;
import com.bookinghotel.mapper.ServiceMapper;
import com.bookinghotel.repository.RoleRepository;
import com.bookinghotel.repository.RoomRepository;
import com.bookinghotel.repository.ServiceRepository;
import com.bookinghotel.repository.UserRepository;
import com.bookinghotel.service.CustomUserDetailsService;
import com.bookinghotel.util.FileUtil;
import com.bookinghotel.util.UploadFileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties({UserInfoProperties.class})
@SpringBootApplication(scanBasePackages = "com.bookinghotel")
public class BookingHotelApplication {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final RoomRepository roomRepository;

    private final ServiceRepository serviceRepository;

    private final CustomUserDetailsService customUserDetailsService;

    private final UploadFileUtil uploadFile;

    private final RoomMapper roomMapper;

    private final ServiceMapper serviceMapper;

    public static void main(String[] args) {
        Environment env = SpringApplication.run(BookingHotelApplication.class, args).getEnvironment();
        String appName = env.getProperty("spring.application.name");
        if (appName != null) {
            appName = appName.toUpperCase();
        }
        String port = env.getProperty("server.port");
        log.info("-------------------------START " + appName
                + " Application------------------------------");
        log.info("   Application         : " + appName);
        log.info("   Url swagger-ui      : http://localhost:" + port + "/swagger-ui.html");
        log.info("-------------------------START SUCCESS " + appName
                + " Application------------------------------");
    }

    @Bean
    CommandLineRunner init(UserInfoProperties userInfo) {
        return args -> {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            //init role
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role(null, RoleConstant.ADMIN, null));
                roleRepository.save(new Role(null, RoleConstant.USER, null));
            }
            //init admin
            if (userRepository.count() == 0) {
                String url = uploadFile.uploadImage(FileUtil.getBytesFileByPath(userInfo.getAvatar()));
                User admin = new User(userInfo.getEmail(), userInfo.getPhone(),
                        passwordEncoder.encode(userInfo.getPassword()),
                        userInfo.getFirstName(), userInfo.getLastName(), userInfo.getGender(),
                        LocalDate.parse(userInfo.getBirthday()),
                        userInfo.getAddress(), Boolean.TRUE, url, roleRepository.findByRoleName(RoleConstant.ADMIN));
                userRepository.save(admin);
            }

            //Login
            User admin = userRepository.findByEmail(userInfo.getEmail()).get();
            UserDetails userDetails = customUserDetailsService.loadUserById(admin.getId());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //init rooms
            if (roomRepository.count() == 0) {
                ObjectMapper objectMapper = new ObjectMapper();
                File file = new File("rooms.json");
                try {
                    Map<String, List<RoomInitJSON>> roomsMap = objectMapper.readValue(file, new TypeReference<>() {
                    });
                    for (Map.Entry<String, List<RoomInitJSON>> entry : roomsMap.entrySet()) {
                        for (RoomInitJSON roomInit : entry.getValue()) {
                            Room room = roomMapper.roomInitToRoom(roomInit);
                            for (Media media : room.getMedias()) {
                                String url = uploadFile.uploadImage(FileUtil.getBytesFileByPath(media.getUrl()));
                                media.setUrl(url);
                                media.setRoom(room);
                            }
                            roomRepository.save(room);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //init services & product
            if (serviceRepository.count() == 0) {
                ObjectMapper objectMapper = new ObjectMapper();
                File file = new File("services.json");
                try {
                    List<ServiceInitJSON> services = objectMapper.readValue(file, new TypeReference<>() {
                    });
                    for (ServiceInitJSON serviceInit : services) {
                        Service service = serviceMapper.serviceInitToService(serviceInit);
                        String urlThumbnailService = uploadFile.uploadImage(FileUtil.getBytesFileByPath(service.getThumbnail()));
                        service.setThumbnail(urlThumbnailService);
                        for (Product product : service.getProducts()) {
                            String urlThumbnailProduct = uploadFile.uploadImage(FileUtil.getBytesFileByPath(product.getThumbnail()));
                            product.setThumbnail(urlThumbnailProduct);
                            product.setService(service);
                        }
                        serviceRepository.save(service);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            SecurityContextHolder.clearContext();
        };
    }

}
