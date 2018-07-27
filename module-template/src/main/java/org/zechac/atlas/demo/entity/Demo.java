package org.zechac.atlas.demo.entity;


import com.alibaba.fastjson.annotation.JSONType;
import org.zechac.atlas.common.constant.Constant;
import org.zechac.atlas.common.jpa.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * 实体
 */
@Table(name = Constant.PERFIXX_TABLE_SYSTEM + "demo")
@Entity
@Data
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
@JSONType(ignores = {"demoOne2Many", "demoMany2Manies"})
public class Demo extends BaseEntity {

    public Demo(String name) {
        this.name = name;
    }

    @Basic
    private String name;

    @JoinColumn(name = "r_damo_one_2_many")
    @ManyToOne(fetch = FetchType.LAZY)
    private DemoOne2Many demoOne2Many;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {})
    @JoinTable(name = "r_demo_many_to_many",
            joinColumns = {@JoinColumn(name = "dome_r_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "demo_id", referencedColumnName = "id")})
    private List<DemoMany2Many> demoMany2Manies;
}
