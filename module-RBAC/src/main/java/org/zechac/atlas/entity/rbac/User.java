package org.zechac.atlas.entity.rbac;

import com.alibaba.fastjson.annotation.JSONType;
import org.zechac.atlas.common.jpa.entity.BaseEntity;
import org.zechac.atlas.rbac.SexType;
import lombok.Data;
import org.zechac.atlas.rbac.SexType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "t_bc_user")
@Entity
@Data
@JSONType(ignores = {"roles"})
public class User extends BaseEntity {

    @Column(name = "u_code")
    private String code;

    /**
     * 登录名
     */
    @Column(name = "u_name")
    private String username;

    @Column(name = "u_password")
    private String password;

    /**
     * 姓名
     */
    private String name;

    @Enumerated
    @Column(name = "u_sex")
    private SexType sex;

    @Column(name = "u_mobile")
    private String mobile;

    @Column(name = "u_tel_phone")
    private String telPhone;

    @Column(name = "u_email")
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "u_birth_date")
    private Date birthday;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "u_join_date")
    private Date joinDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "u_leave_date")
    private Date leaveDate;

    @Column(name = "u_status")
    private int status;

    @Transient
    private String token;

    @ManyToMany
    @JoinTable(name = "r_user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;
}
