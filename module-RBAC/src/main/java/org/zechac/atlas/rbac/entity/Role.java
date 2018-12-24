package org.zechac.atlas.rbac.entity;

import com.alibaba.fastjson.annotation.JSONType;
import org.zechac.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 角色
 */
@Table(name = "rbac_role")
@Entity
@Data
@JSONType(ignores = {"users"})
public class Role extends BaseEntity {

    private String name;

    private String code;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany
    @JoinTable(name = "r_role_resource",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "resource_id", referencedColumnName = "id")})
    private List<Resource> resources;
}
