package org.zechac.atlas.service;

import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.entity.rbac.Organ;
import org.zechac.atlas.repo.OrganRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by My on 2018-03-21.
 */
@Service
@Transactional
public class OrganService extends BaseServiceImpl<Organ, OrganRepo> {
    /**
     * @param pId 上级资源 ID
     * @return List
     * @throws Exception
     * @desc 拼接选择的资源树
     */
    public List buildOrgChild(String pId) {

        List<Organ> organs = entityDao.runSql(pId);

        return buildOrgParent(organs);
    }


    /**
     * @param treeP 节点列表
     * @return List
     * @throws Exception 基类
     * @desc 获取当前节点的子节点，并生成树状结点，结合buildResChild使用
     */
    private List buildOrgParent(List<Organ> treeP) {
        if (treeP.size() == 0) {
            return treeP;
        }
        List<Organ> childList;
        for (Organ organ : treeP) {
            String resId = String.valueOf(organ.getId());
            childList = buildOrgChild(resId);
            //存在子节点，增加子节点 返回
            if (organ.getOrganList() != null) {
                organ.getOrganList().addAll(childList);
            } else {
                organ.setOrganList(new ArrayList<>());
                organ.getOrganList().addAll(childList);
            }
        }
        return treeP;
    }

}
