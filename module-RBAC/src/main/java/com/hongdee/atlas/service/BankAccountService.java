package com.hongdee.atlas.service;

/**
 * Created by My on 2018-03-28.
 */

import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.entity.rbac.BankAccount;
import com.hongdee.atlas.repo.BankAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
*@author作者：王恒腾
*类名：
*功能：银行卡方法类
*时间：2018-03-28/20:30
*
*/
@Service
@Transactional
public class BankAccountService extends BaseServiceImpl<BankAccount,BankAccountRepo>{
}
