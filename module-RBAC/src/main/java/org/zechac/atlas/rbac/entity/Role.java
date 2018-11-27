package org.zechac.atlas.rbac.entity;

import com.alibaba.fastjson.annotation.JSONType;
import org.zechac.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * 角色
 */
@Table(name = "t_bc_role")
@Entity
@Data
@JSONType(ignores = {"users"})
public class Role extends BaseEntity {

    private String name;

    private String code;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
