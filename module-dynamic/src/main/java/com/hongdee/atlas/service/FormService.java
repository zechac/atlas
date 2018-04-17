package com.hongdee.atlas.service;

import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.entity.dynamic.DynamicForm;
import com.hongdee.atlas.repo.FormRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("dynamicFormService")
@Transactional
public class FormService extends BaseServiceImpl<DynamicForm,FormRepo> {
}
