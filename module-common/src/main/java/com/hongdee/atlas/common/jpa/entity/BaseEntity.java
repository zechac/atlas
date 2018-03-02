package com.hongdee.atlas.common.jpa.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by zhaozh on 2016-5-17.
 */
@MappedSuperclass
@Data
@Access(AccessType.FIELD)
public class BaseEntity  extends AuditingEntity{
}
