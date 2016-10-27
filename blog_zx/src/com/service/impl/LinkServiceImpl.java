package com.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.dao.LinkDao;
import com.entity.Link;
import com.service.LinkService;

/**
 * 友情链接Service实现类
 * @author Administrator
 *
 */
public class LinkServiceImpl implements LinkService{

	@Resource
	private LinkDao linkDao;

	public List<Link> list() {
		return linkDao.list();
	}

	
	
	

}
