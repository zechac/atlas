package org.zechac.atlas.rbac.service;

import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.rbac.entity.dynamic.DynamicForm;
import org.zechac.atlas.rbac.repo.FormRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("dynamicFormService")
@Transactional
public class FormService extends BaseServiceImpl<DynamicForm, FormRepo> {
}
