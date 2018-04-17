package com.hongdee.atlas.entity.rbac;


import com.hongdee.atlas.common.jpa.entity.BaseEntity;
import com.hongdee.atlas.rbac.MenuStatus;
import com.hongdee.atlas.rbac.OrganStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
*@author作者：王恒腾
*类名：部门实体类
*功能：部门实体类
*时间：2018-03-21/14:44
*
*/
@Table(name="t_bc_organ")
@Entity
@Data
public class Organ extends BaseEntity {
    @Column(name="o_address")
    private String address;
    @Column(name="o_provance")
    private String provance;
    @Column(name="o_city")
    private String city;
    @Column(name="o_county")
    private String county;
    @Column(name="o_type")
    private String type;
    @Column(name="o_name")
    private String name;
    @Column(name="o_p_id")
    private String pId;
    @Column(name="o_phone")
    private String phone;
    @Column(name="o_zip_code")
    private String zipCode;
    @Column(name="o_short_name")
    private String shortName;
    @Column(name="o_status")
    private OrganStatus status;
    @Column(name="o_leader")
    private String leader;
    @Column(name="o_merchant_code")
    private String merchantCode;
    @Column(name="o_accounting_code")
    private String accountingCode;
    @Column(name="o_business_license")
    private String businessLicense;
    @Column(name="o_jurisdictional_area")
    private String jurisdictionalArea;
    @Column(name="o_disp_order")
    private String dispOrder;


    /**
     *
     */
    @Transient
    private List<Organ> organList;

    @OneToMany(mappedBy = "organ",cascade = {CascadeType.ALL})
    private List<BankAccount>  bankAccounts;

}
