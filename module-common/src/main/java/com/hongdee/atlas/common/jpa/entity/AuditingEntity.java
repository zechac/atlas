package com.hongdee.atlas.common.jpa.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhaozh on 2016/6/29.
 */
@MappedSuperclass
@Data
@Access(AccessType.FIELD)
@EntityListeners(AuditingEntityListener.class)
public class AuditingEntity extends IDEntity{
    @CreatedBy
    @Column(name = "create_user_id",length = 32,updatable = false,nullable = true)
    protected String createUserId;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time",updatable = false,nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createDatetime;

    @LastModifiedBy
    @Column(name = "last_update_user_id",length = 32,nullable = true)
    protected String lastUpdateUserId;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update_time",nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date lastUpdateDatetime;
}
