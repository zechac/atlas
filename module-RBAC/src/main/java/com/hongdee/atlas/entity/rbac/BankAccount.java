package com.hongdee.atlas.entity.rbac;

/**
 * Created by My on 2018-03-28.
 */

import com.alibaba.fastjson.annotation.JSONType;
import com.hongdee.atlas.common.jpa.entity.BaseEntity;
import com.hongdee.atlas.rbac.BankAccountStatus;
import lombok.Data;

import javax.persistence.*;

/**
*@author作者：王恒腾
*类名：
*功能：银行卡实体
*时间：2018-03-28/20:18
*
*/
@Table(name="t_bc_bank_account")
@Entity
@Data
@JSONType(ignores = {"organ"})
public class BankAccount extends BaseEntity {
    @Column(name="a_account_name")
    private String accountName;
    @Column(name="a_account_opening_bank")
    private String accountOpeningBank;
    @JoinColumn
    @ManyToOne
    private Organ organ;
    @Column(name="a_bank_account")
    private String bankAccount;
    @Column(name="a_status")
    private BankAccountStatus status;
    @Column(name="a_type")
    private String type;
    @Column(name="a_b_remarks")
    private String bRemarks;

}
