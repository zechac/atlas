package com.hongdee.atlas.service;

import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.entity.rbac.Role;
import com.hongdee.atlas.repo.RoleRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleService extends BaseServiceImpl<Role,RoleRepo> {
}

