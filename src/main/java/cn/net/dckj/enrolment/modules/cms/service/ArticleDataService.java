/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package cn.net.dckj.enrolment.modules.cms.service;

import cn.net.dckj.enrolment.modules.cms.entity.ArticleData;
import cn.net.dckj.enrolment.common.service.CrudService;
import cn.net.dckj.enrolment.modules.cms.dao.ArticleDataDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 站点Service
 * @author ZYM
 * @version 2018-01-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleDataService extends CrudService<ArticleDataDao, ArticleData> {

}
