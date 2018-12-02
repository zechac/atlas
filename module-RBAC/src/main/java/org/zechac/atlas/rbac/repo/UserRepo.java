package org.zechac.atlas.rbac.repo;

import org.zechac.atlas.common.repo.BaseRepo;
import org.zechac.atlas.rbac.entity.User;

public interface UserRepo extends BaseRepo<User, String> {
    User findByUsername(String s);
}
