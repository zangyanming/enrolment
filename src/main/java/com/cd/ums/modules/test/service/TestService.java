/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.test.entity.Test;
import com.cd.ums.modules.test.dao.TestDao;

/**
 * 测试Service
 * @author ZYM
 * @version 2018-10-17
 */
@Service
@Transactional(readOnly = true)
public class TestService extends CrudService<TestDao, Test> {

}
