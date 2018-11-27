package org.zechac.atlas.rbac.service;

import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.rbac.entity.Role;
import org.zechac.atlas.rbac.repo.RoleRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleService extends BaseServiceImpl<Role, RoleRepo> {
}

