package org.zechac.atlas.rbac.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zechac.atlas.rbac.entity.Role;
import org.zechac.atlas.rbac.repo.UserRepo;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class SecurityUserService implements UserDetailsService {

    @Autowired
    UserRepo userRepository;

    /**
     * 加载用户
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        org.zechac.atlas.rbac.entity.User user = userRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        System.out.println("s:"+s);
        System.out.println("username:"+user.getUsername()+";password:"+user.getPassword());
        //System.out.println("size:"+user.getRoles().size());
        //System.out.println("role:"+user.getRoles().get(0).getName());
        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        for (Role role :user.getRoles()){
            GrantedAuthority grantedAuthority=new SimpleGrantedAuthority(role.getCode());
            grantedAuthorities.add(grantedAuthority);
        }
        UserDetails userDetails=new User(user.getUsername(),user.getPassword(),grantedAuthorities);
        return userDetails;
    }
}

