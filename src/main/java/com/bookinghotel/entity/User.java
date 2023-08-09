package com.bookinghotel.entity;

import com.bookinghotel.entity.common.DateAuditing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends DateAuditing {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private String id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Nationalized
    @Column(nullable = false)
    private String firstName;

    @Nationalized
    @Column(nullable = false)
    private String lastName;

    @Nationalized
    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private LocalDate birthday;

    @Nationalized
    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Boolean isEnable;

    private String avatar;

    @Column(nullable = false)
    private Boolean isLocked;

    //Link to table Role
    @ManyToOne
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_USER_ROLE"))
    private Role role;

    //Link to table VerificationToken
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private VerificationToken verificationToken;

    //Link to table Booking
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private Set<Booking> bookings = new HashSet<>();

    //Link to table Post
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @Where(clause = "delete_flag = 0")
    @JsonIgnore
    private Set<Post> posts = new HashSet<>();

    public User(String email, String phoneNumber, String password, String firstName, String lastName,
                String gender, LocalDate birthday, String address, Boolean enabled, String avatar, Role role) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.isEnable = enabled;
        this.avatar = avatar;
        this.role = role;
    }

    @PrePersist
    public void prePersist() {
        if (this.isEnable == null) {
            this.isEnable = Boolean.FALSE;
        }
        if (this.isLocked == null) {
            this.isLocked = Boolean.FALSE;
        }
    }

}
