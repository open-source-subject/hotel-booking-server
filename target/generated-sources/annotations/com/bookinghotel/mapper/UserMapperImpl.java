package com.bookinghotel.mapper;

import com.bookinghotel.dto.UserCreateDTO;
import com.bookinghotel.dto.UserDTO;
import com.bookinghotel.dto.UserSummaryDTO;
import com.bookinghotel.dto.UserUpdateDTO;
import com.bookinghotel.dto.common.CreatedByDTO;
import com.bookinghotel.dto.common.LastModifiedByDTO;
import com.bookinghotel.entity.Role;
import com.bookinghotel.entity.User;
import com.bookinghotel.repository.projection.StatisticCustomerTopBookingProjection;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-18T09:20:28+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserCreateDTO userCreateDTO) {
        if ( userCreateDTO == null ) {
            return null;
        }

        User user = new User();

        if ( userCreateDTO.getEmail() != null ) {
            user.setEmail( userCreateDTO.getEmail() );
        }
        if ( userCreateDTO.getPhoneNumber() != null ) {
            user.setPhoneNumber( userCreateDTO.getPhoneNumber() );
        }
        if ( userCreateDTO.getPassword() != null ) {
            user.setPassword( userCreateDTO.getPassword() );
        }
        if ( userCreateDTO.getFirstName() != null ) {
            user.setFirstName( userCreateDTO.getFirstName() );
        }
        if ( userCreateDTO.getLastName() != null ) {
            user.setLastName( userCreateDTO.getLastName() );
        }
        if ( userCreateDTO.getGender() != null ) {
            user.setGender( userCreateDTO.getGender() );
        }
        if ( userCreateDTO.getBirthday() != null ) {
            user.setBirthday( userCreateDTO.getBirthday() );
        }
        if ( userCreateDTO.getAddress() != null ) {
            user.setAddress( userCreateDTO.getAddress() );
        }

        return user;
    }

    @Override
    public UserDTO toUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        String roleName = userRoleRoleName( user );
        if ( roleName != null ) {
            userDTO.setRoleName( roleName );
        }
        if ( user.getCreatedDate() != null ) {
            userDTO.setCreatedDate( user.getCreatedDate() );
        }
        if ( user.getLastModifiedDate() != null ) {
            userDTO.setLastModifiedDate( user.getLastModifiedDate() );
        }
        if ( user.getId() != null ) {
            userDTO.setId( user.getId() );
        }
        if ( user.getEmail() != null ) {
            userDTO.setEmail( user.getEmail() );
        }
        if ( user.getPhoneNumber() != null ) {
            userDTO.setPhoneNumber( user.getPhoneNumber() );
        }
        if ( user.getFirstName() != null ) {
            userDTO.setFirstName( user.getFirstName() );
        }
        if ( user.getLastName() != null ) {
            userDTO.setLastName( user.getLastName() );
        }
        if ( user.getGender() != null ) {
            userDTO.setGender( user.getGender() );
        }
        if ( user.getBirthday() != null ) {
            userDTO.setBirthday( user.getBirthday() );
        }
        if ( user.getAddress() != null ) {
            userDTO.setAddress( user.getAddress() );
        }
        if ( user.getAvatar() != null ) {
            userDTO.setAvatar( user.getAvatar() );
        }
        if ( user.getIsEnable() != null ) {
            userDTO.setIsEnable( user.getIsEnable() );
        }
        if ( user.getIsLocked() != null ) {
            userDTO.setIsLocked( user.getIsLocked() );
        }

        return userDTO;
    }

    @Override
    public UserSummaryDTO toUserSummaryDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserSummaryDTO userSummaryDTO = new UserSummaryDTO();

        if ( user.getId() != null ) {
            userSummaryDTO.setId( user.getId() );
        }
        if ( user.getEmail() != null ) {
            userSummaryDTO.setEmail( user.getEmail() );
        }
        if ( user.getPhoneNumber() != null ) {
            userSummaryDTO.setPhoneNumber( user.getPhoneNumber() );
        }
        if ( user.getFirstName() != null ) {
            userSummaryDTO.setFirstName( user.getFirstName() );
        }
        if ( user.getLastName() != null ) {
            userSummaryDTO.setLastName( user.getLastName() );
        }
        if ( user.getAvatar() != null ) {
            userSummaryDTO.setAvatar( user.getAvatar() );
        }

        return userSummaryDTO;
    }

    @Override
    public List<UserDTO> toUserDTOs(List<User> user) {
        if ( user == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( user.size() );
        for ( User user1 : user ) {
            list.add( toUserDTO( user1 ) );
        }

        return list;
    }

    @Override
    public void updateUserFromDTO(UserUpdateDTO updateDTO, User user) {
        if ( updateDTO == null ) {
            return;
        }

        if ( updateDTO.getPhoneNumber() != null ) {
            user.setPhoneNumber( updateDTO.getPhoneNumber() );
        }
        if ( updateDTO.getFirstName() != null ) {
            user.setFirstName( updateDTO.getFirstName() );
        }
        if ( updateDTO.getLastName() != null ) {
            user.setLastName( updateDTO.getLastName() );
        }
        if ( updateDTO.getBirthday() != null ) {
            user.setBirthday( updateDTO.getBirthday() );
        }
        if ( updateDTO.getAddress() != null ) {
            user.setAddress( updateDTO.getAddress() );
        }
    }

    @Override
    public CreatedByDTO toCreatedByDTO(User creator) {
        if ( creator == null ) {
            return null;
        }

        CreatedByDTO createdByDTO = new CreatedByDTO();

        if ( creator.getId() != null ) {
            createdByDTO.setId( creator.getId() );
        }
        if ( creator.getFirstName() != null ) {
            createdByDTO.setFirstName( creator.getFirstName() );
        }
        if ( creator.getLastName() != null ) {
            createdByDTO.setLastName( creator.getLastName() );
        }
        if ( creator.getAvatar() != null ) {
            createdByDTO.setAvatar( creator.getAvatar() );
        }

        return createdByDTO;
    }

    @Override
    public LastModifiedByDTO toLastModifiedByDTO(User updater) {
        if ( updater == null ) {
            return null;
        }

        LastModifiedByDTO lastModifiedByDTO = new LastModifiedByDTO();

        if ( updater.getId() != null ) {
            lastModifiedByDTO.setId( updater.getId() );
        }
        if ( updater.getFirstName() != null ) {
            lastModifiedByDTO.setFirstName( updater.getFirstName() );
        }
        if ( updater.getLastName() != null ) {
            lastModifiedByDTO.setLastName( updater.getLastName() );
        }
        if ( updater.getAvatar() != null ) {
            lastModifiedByDTO.setAvatar( updater.getAvatar() );
        }

        return lastModifiedByDTO;
    }

    @Override
    public UserDTO statisticCustomerTopBookingProjectionToUserDTO(StatisticCustomerTopBookingProjection projection) {
        if ( projection == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        if ( projection.getCreatedDate() != null ) {
            userDTO.setCreatedDate( projection.getCreatedDate() );
        }
        if ( projection.getLastModifiedDate() != null ) {
            userDTO.setLastModifiedDate( projection.getLastModifiedDate() );
        }
        if ( projection.getId() != null ) {
            userDTO.setId( projection.getId() );
        }
        if ( projection.getEmail() != null ) {
            userDTO.setEmail( projection.getEmail() );
        }
        if ( projection.getPhoneNumber() != null ) {
            userDTO.setPhoneNumber( projection.getPhoneNumber() );
        }
        if ( projection.getFirstName() != null ) {
            userDTO.setFirstName( projection.getFirstName() );
        }
        if ( projection.getLastName() != null ) {
            userDTO.setLastName( projection.getLastName() );
        }
        if ( projection.getGender() != null ) {
            userDTO.setGender( projection.getGender() );
        }
        if ( projection.getBirthday() != null ) {
            userDTO.setBirthday( projection.getBirthday() );
        }
        if ( projection.getAddress() != null ) {
            userDTO.setAddress( projection.getAddress() );
        }
        if ( projection.getAvatar() != null ) {
            userDTO.setAvatar( projection.getAvatar() );
        }
        if ( projection.getRoleName() != null ) {
            userDTO.setRoleName( projection.getRoleName() );
        }
        if ( projection.getIsEnable() != null ) {
            userDTO.setIsEnable( projection.getIsEnable() );
        }
        if ( projection.getIsLocked() != null ) {
            userDTO.setIsLocked( projection.getIsLocked() );
        }

        return userDTO;
    }

    private String userRoleRoleName(User user) {
        if ( user == null ) {
            return null;
        }
        Role role = user.getRole();
        if ( role == null ) {
            return null;
        }
        String roleName = role.getRoleName();
        if ( roleName == null ) {
            return null;
        }
        return roleName;
    }
}
