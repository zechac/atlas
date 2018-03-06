package com.hongdee.atlas.demo.entity;


import com.hongdee.atlas.common.constant.Constant;
import com.hongdee.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name ="demo_one_2_many")
@Data
@Cacheable
@Entity
public class DemoOne2Many extends BaseEntity{

    @Basic
    private String name;

    @OneToMany(mappedBy = "demoOne2Many")
    private List<Demo> demos;

}
