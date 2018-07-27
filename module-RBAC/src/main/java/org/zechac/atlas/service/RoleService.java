package org.zechac.atlas.service;

import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.entity.rbac.Role;
import org.zechac.atlas.repo.RoleRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleService extends BaseServiceImpl<Role, RoleRepo> {
}

