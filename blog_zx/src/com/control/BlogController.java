package com.control;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entity.Blog;
import com.service.BlogService;
import com.util.StringUtil;

/**
 * 博客Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

	@Resource
	private BlogService blogService;
	
	/**
	 * 请求博客详细信息
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/articles/{id}")
	public ModelAndView details(@PathVariable("id") Integer id,HttpServletRequest request)throws Exception{
		ModelAndView mav=new ModelAndView();
		Blog blog=blogService.findById(id);
		mav.addObject("blog",blog);
		
		String keyWord = blog.getKeyWord();
		if(StringUtil.isNotEmpty(keyWord)){
			String[] keyWords = keyWord.split(" ");
			mav.addObject("keyWords", StringUtil.filterWhite(Arrays.asList(keyWords)));
		}else{
			mav.addObject("keyWords", null);
		}
		
		
		
		// 给博客浏览次数加1
		blog.setClickHit(blog.getClickHit()+1);
		blogService.update(blog);
		
		mav.addObject("pageCode", this.getUpAndDownPageCode(blogService.getLastBlog(id), blogService.getNextBlog(id), request.getServletContext().getContextPath()));
		mav.addObject("pageTitle", blog.getTitle()+"java开源博客系统");
		mav.addObject("mainPage", "foreground/blog/view.jsp");
		mav.setViewName("mainTemp");
		return mav;
	}
	
	/**
	 * 获取上一篇博客和下一篇博客
	 * @param lastBlog
	 * @param nextBlog
	 * @param projectContext
	 * @return
	 */
	private String getUpAndDownPageCode(Blog lastBlog,Blog nextBlog,String projectContext){
		StringBuffer pageCode=new StringBuffer();
		if(lastBlog==null || lastBlog.getId()==null){
			pageCode.append("<p>上一篇：没有了</p>");
		}else{
			pageCode.append("<p>上一篇：<a href='"+projectContext+"/blog/articles/"+lastBlog.getId()+".html'>"+lastBlog.getTitle()+"</a></p>");
		}
			
		if(nextBlog==null || nextBlog.getId()==null){
			pageCode.append("<p>下一篇：没有了</p>");
		}else{
			pageCode.append("<p>下一篇：<a href='"+projectContext+"/blog/articles/"+nextBlog.getId()+".html'>"+nextBlog.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}
	
	
	
}
