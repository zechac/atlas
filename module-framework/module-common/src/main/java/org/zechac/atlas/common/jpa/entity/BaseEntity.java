package org.zechac.atlas.common.jpa.entity;

import lombok.Data;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

/**
 * Created by zhaozh on 2016-5-17.
 */
@MappedSuperclass
@Data
@Access(AccessType.FIELD)
public class BaseEntity extends AuditingEntity {
}
