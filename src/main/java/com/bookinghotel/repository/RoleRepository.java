package com.bookinghotel.repository;

import com.bookinghotel.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.id = ?1")
    Optional<Role> findById(Long id);

    @Query("SELECT r FROM Role r WHERE r.roleName = ?1")
    Role findByRoleName(String roleName);

}
