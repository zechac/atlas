package org.zechac.atlas.metadata.service;

import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.metadata.entity.Dict;
import org.zechac.atlas.metadata.repo.DictRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by My on 2018-04-01.
 */
@Service
@Transactional
public class DictService extends BaseServiceImpl<Dict, DictRepo> {
    public List buildDictChild(String pId) {

        List<Dict> dicts = entityDao.runSql(pId);

        return buildDictParent(dicts);
    }

    private List buildDictParent(List<Dict> treeP) {
        if (treeP.size() == 0) {
            return treeP;
        }
        List<Dict> childList;
        for (Dict dict : treeP) {
            String dictId = String.valueOf(dict.getDCode());
            childList = buildDictChild(dictId);
            //存在子节点，增加子节点 返回
            if (dict.getDictList() != null) {
                dict.getDictList().addAll(childList);
            } else {
                dict.setDictList(new ArrayList<>());
                dict.getDictList().addAll(childList);
            }
        }
        return treeP;
    }
}
