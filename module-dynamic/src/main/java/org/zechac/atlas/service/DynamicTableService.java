package org.zechac.atlas.service;

import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.entity.dynamic.DynamicTable;
import org.zechac.atlas.repo.DynamicTableRepo;
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
public class DynamicTableService extends BaseServiceImpl<DynamicTable, DynamicTableRepo> {
}
