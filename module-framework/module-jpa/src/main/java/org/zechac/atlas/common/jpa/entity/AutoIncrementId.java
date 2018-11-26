package org.zechac.atlas.common.jpa.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by hua on 2017/1/20.
 */
@MappedSuperclass
@Data
@Access(AccessType.FIELD)
public class AutoIncrementId implements SuperEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
