package com.bookinghotel.entity.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
public abstract class FlagUserDateAuditing extends UserDateAuditing {

    @Column(nullable = false)
    private Boolean deleteFlag = Boolean.FALSE;

}
