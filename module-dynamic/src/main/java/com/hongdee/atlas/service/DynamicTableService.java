package com.hongdee.atlas.service;

import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.entity.dynamic.DynamicTable;
import com.hongdee.atlas.repo.DynamicTableRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @param
 * @param
 * @param
 * @return
 * @throws
 */
@Service
@Transactional
public class DynamicTableService extends BaseServiceImpl<DynamicTable,DynamicTableRepo> {
}
