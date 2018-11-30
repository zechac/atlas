package org.zechac.atlas.rbac.service;

import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.rbac.entity.Resource;
import org.zechac.atlas.rbac.entity.Role;
import org.zechac.atlas.rbac.repo.RoleRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleService extends BaseServiceImpl<Role, RoleRepo> {
    public List<Role> findByResource(Resource resource) {
       return entityDao.findByResources(resource);
    }
}

