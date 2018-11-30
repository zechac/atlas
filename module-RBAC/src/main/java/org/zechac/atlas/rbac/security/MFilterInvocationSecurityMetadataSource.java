package org.zechac.atlas.rbac.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.zechac.atlas.rbac.entity.Resource;
import org.zechac.atlas.rbac.entity.User;
import org.zechac.atlas.rbac.service.ResourceService;
import org.zechac.atlas.rbac.service.RoleService;
import org.zechac.atlas.rbac.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class MFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    public RoleService roleService ;
    @Autowired
    private UserService userService ;
    @Autowired
    private ResourceService resourceService ;

    private AntPathRequestMatcher urlMatcher = new AntPathRequestMatcher("/**");
    // 资源权限集合
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
    public void loadResourceDefine(){
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
//        //取得用户信息
//        List<User> userList = userService.findAll();
//        //取得资源与角色列表
//        List<Resource> resourceList = resourceService.findAll();
//        System.out.println(resourceList);
//        for (Resource resource : resourceList) {
//            Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
//            //atts.add(new SecurityConfig(resource.getRoleName() ));
//            //resourceMap.put(resource.getResString(), atts);
//        }
    }
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        loadResourceDefine();//防止无法注入问题
        // guess object is a URL.
        HttpServletRequest request = ((FilterInvocation) o).getRequest();
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            if (urlMatcher.matches(request)) {
                return resourceMap.get(resURL);
            }
        }
        return null;
    }
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}