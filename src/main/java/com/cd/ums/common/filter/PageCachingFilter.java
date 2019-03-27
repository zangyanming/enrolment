/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.common.filter;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

import com.cd.ums.common.utils.SpringContextHolder;

/**
 * 页面高速缓存过滤器
 * @author ZYM
 * @version 2018-8-5
 */
public class PageCachingFilter extends SimplePageCachingFilter {

	private CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);
	
	@Override
	protected CacheManager getCacheManager() {
		return cacheManager;
	}
	
}
