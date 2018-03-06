package com.hongdee.atlas.demo.service;

import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.demo.entity.Demo;
import com.hongdee.atlas.demo.repo.DemoRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DemoService extends BaseServiceImpl<Demo,DemoRepo> {

}
