/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.cms.service;

import com.cd.ums.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.cms.dao.ArticleDataDao;
import com.cd.ums.modules.cms.entity.ArticleData;

/**
 * 站点Service
 * @author ZYM
 * @version 2018-01-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleDataService extends CrudService<ArticleDataDao, ArticleData> {

}
