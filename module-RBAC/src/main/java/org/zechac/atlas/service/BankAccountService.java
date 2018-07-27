package org.zechac.atlas.service;

/**
 * Created by My on 2018-03-28.
 */

import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.entity.rbac.BankAccount;
import org.zechac.atlas.repo.BankAccountRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author作者：王恒腾 类名：
 * 功能：银行卡方法类
 * 时间：2018-03-28/20:30
 */
@Service
@Transactional
public class BankAccountService extends BaseServiceImpl<BankAccount, BankAccountRepo> {
}
