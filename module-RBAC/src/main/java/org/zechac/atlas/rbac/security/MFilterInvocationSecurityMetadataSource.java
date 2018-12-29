package org.zechac.atlas.rbac.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.zechac.atlas.rbac.entity.Resource;
import org.zechac.atlas.rbac.entity.Role;
import org.zechac.atlas.rbac.entity.User;
import org.zechac.atlas.rbac.service.ResourceService;
import org.zechac.atlas.rbac.service.RoleService;
import org.zechac.atlas.rbac.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Transactional
public class MFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {

    // 资源权限集合
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    @Autowired
    public RoleService roleService ;
    @Autowired
    private UserService userService ;
    @Autowired
    private ResourceService resourceService ;

    public MFilterInvocationSecurityMetadataSource() {
    }

    public void loadResourceDefine(){
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        //取得资源与角色列表
        List<Resource> resourceList = resourceService.findAll();
        //System.out.println(resourceList);
        for (Resource resource : resourceList) {
            Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
            List<Role> roles=roleService.findByResource(resource);
            for(Role role :roles){
                atts.add(new SecurityConfig(role.getCode()));
            }
            resourceMap.put(resource.getUrl(), atts);
        }
    }

    /**
     * 获取访问资源需要的角色
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //loadResourceDefine();//防止无法注入问题
        // guess object is a URL.
        HttpServletRequest request = ((FilterInvocation) o).getRequest();
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            AntPathRequestMatcher urlMatcher = new AntPathRequestMatcher(resURL);
            if (urlMatcher.matches(request)) {
                return resourceMap.get(resURL);
            }
        }
        return null;
    }

    /**
     * 获取所有角色
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();

        for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap
                .entrySet()) {
            allAttributes.addAll(entry.getValue());
        }

        return allAttributes;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadResourceDefine();
    }
}