package com.hongdee.atlas.service;

import com.hongdee.atlas.common.cache.CacheManager;
import com.hongdee.atlas.common.cache.CacheProvider;
import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.entity.rbac.User;
import com.hongdee.atlas.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional
public class UserService extends BaseServiceImpl<User,UserRepo>{

    @Autowired
    private CacheProvider cacheProvider;

    public User login(User user){
        Map q=new LinkedHashMap();
        q.put("username$EQ",user.getUsername());
        q.put("mobile$EQ$OR",user.getUsername());
        q.put("password",user.getPassword());
        User user1=queryOne(q);
        if(user1!=null){
            user1.setToken(user1.getId());
            cacheProvider.put(user1.getId(),user1);
        }
        return user1;
    }
}
