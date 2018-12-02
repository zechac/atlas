package org.zechac.atlas.rbac.repo;

import org.zechac.atlas.common.repo.BaseRepo;
import org.zechac.atlas.rbac.entity.Resource;
import org.zechac.atlas.rbac.entity.Role;

import java.util.List;

public interface RoleRepo extends BaseRepo<Role, String> {
    List<Role> findByResources(Resource resource);
}
