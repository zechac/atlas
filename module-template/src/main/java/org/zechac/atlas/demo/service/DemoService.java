package org.zechac.atlas.demo.service;

import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.demo.entity.Demo;
import org.zechac.atlas.demo.repo.DemoRepo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DemoService extends BaseServiceImpl<Demo, DemoRepo> {

    @Autowired
    private SqlSession sqlSession;

}
