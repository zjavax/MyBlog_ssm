package com.control;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Blog;
import com.entity.PageBean;
import com.service.BlogService;
import com.util.PageUtil;
import com.util.StringUtil;

/**
 * 主页Contrller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class IndexContrller {

	@Resource
	private BlogService blogService;
	
	/**
	 * 请求主页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model,HttpServletRequest request,
			@RequestParam(value="page",required=false)String page,
			@RequestParam(value="typeId",required=false)String typeId,
			@RequestParam(value="releaseDateStr",required=false)String releaseDateStr)throws Exception{
		
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		
		PageBean pageBean = new PageBean();
		pageBean.setPage(Integer.parseInt(page));

		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("typeId", typeId);
		if(StringUtil.isNotEmpty(releaseDateStr)){
			releaseDateStr = new String(releaseDateStr.getBytes("iso-8859-1"),"UTF-8");
		}
		map.put("releaseDateStr", releaseDateStr);
		 
		List<Blog> blogList=blogService.list(map);
		
		// 使用Jsoup解析出html中的img元素
		for(Blog blog:blogList){
			String blogInfo=blog.getContent();
			Document doc=Jsoup.parse(blogInfo);
			Elements jpgs=doc.select("img[src$=.jpg]");
			
			List<String> imageList = new LinkedList<String>();
			for(int i=0;i<jpgs.size();i++){
				Element jpg=jpgs.get(i);
				imageList.add(jpg.toString());
				blog.setImageList(imageList);
				
				if(i==2){  //首页只展示3张图片
					break;
				}
			}
		}
		
		model.addAttribute("blogList", blogList);
		
		StringBuffer param=new StringBuffer();
		if(StringUtil.isNotEmpty(typeId)){
			param.append("typeId"+typeId+"&");
		}
		if(StringUtil.isNotEmpty(releaseDateStr)){
			param.append("releaseDateStr"+releaseDateStr+"&");
		}
		
		model.addAttribute("pageCode", PageUtil.genPagination(request.getContextPath()+"/index.html", blogService.getTotal(), Integer.parseInt(page), pageBean.getPageSize(), param.toString()));
		model.addAttribute("pageTitle", "java开源博客系统");
		model.addAttribute("mainPage", "foreground/blog/list.jsp");
		
		return "mainTemp";
	}
}
