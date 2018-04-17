package com.hongdee.atlas.demo.service;

import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.demo.entity.Demo;
import com.hongdee.atlas.demo.repo.DemoRepo;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class DemoService extends BaseServiceImpl<Demo,DemoRepo> {

    @Autowired
    private SqlSession sqlSession;

}
