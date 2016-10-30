package com.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.BlogDao;
import com.entity.Blog;
import com.service.BlogService;

/**
 * 博客Service实现类
 * @author Administrator
 *
 */
public class BlogServiceImpl implements BlogService{

	@Resource
	private BlogDao blogDao;
	
	public List<Blog> countList() {
		return blogDao.countList();
	}

	public List<Blog> list(Map<String, Object> map) {
		return blogDao.list(map);
	}

	public Long getTotal() {
		return blogDao.getTotal();
	}
	
	public Blog findById(Integer id) {
		return blogDao.findById(id);
	}
	
	public Integer update(Blog blog) {
		return blogDao.update(blog);
	}
	
	public Blog getLastBlog(Integer id) {
		return blogDao.findById(id-1);
	}

	public Blog getNextBlog(Integer id) {
		return blogDao.findById(id+1);
	}

	 
}
