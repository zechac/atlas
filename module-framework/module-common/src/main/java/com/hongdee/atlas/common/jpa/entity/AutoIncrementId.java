package com.hongdee.atlas.common.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by hua on 2017/1/20.
 */
@MappedSuperclass
@Data
@Access(AccessType.FIELD)
public class AutoIncrementId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
