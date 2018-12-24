package org.zechac.atlas.rbac.entity;

import com.alibaba.fastjson.annotation.JSONType;
import org.zechac.atlas.common.jpa.entity.BaseEntity;
import org.zechac.atlas.rbac.SexType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "rbac_user")
@Entity
@Data
@JSONType(ignores = {"roles"})
public class User extends BaseEntity {

    private String code;

    /**
     * 登录名
     */
    private String username;

    private String password;

    /**
     * 姓名
     */
    private String nickname;

    @Enumerated
    private SexType sex;

    private String mobile;

    private String telPhone;

    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;

    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date leaveDate;

    /**
     * 0 禁用
     * 1 启用
     */
    private int status;

    @Transient
    private String token;

    @ManyToMany
    @JoinTable(name = "r_user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;
}
