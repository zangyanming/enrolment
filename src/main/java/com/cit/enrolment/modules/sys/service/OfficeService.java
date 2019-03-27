/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cit.enrolment.modules.sys.service;

import com.cit.enrolment.common.service.TreeService;
import com.cit.enrolment.modules.sys.dao.OfficeDao;
import com.cit.enrolment.modules.sys.entity.Office;
import com.cit.enrolment.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构Service
 * @author ZYM
 * @version 2019-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		if(office != null){
			office.setParentIds(office.getParentIds()+"%");
			return dao.findByParentIdsLike(office);
		}
		return  new ArrayList<Office>();
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	public List<Office> findOfficePersonList(Office office) {
		return dao.findOfficePersonList(office);
	}

	public String getParentOrgIdPath(String id) {
		return getParentOrgIds(id, "");
	}

	private String getParentOrgIds(String id, String childIds) {
		Office office = this.get(id);
		String parentId = office.getParentId();
		if (childIds.equals("")) {
			childIds = parentId + ",";
		} else {
			childIds = parentId + "," + childIds;
		}
		if (!parentId.equals("0")) {
			childIds = getParentOrgIds(parentId, childIds);
		}
		return childIds;
	}
}
