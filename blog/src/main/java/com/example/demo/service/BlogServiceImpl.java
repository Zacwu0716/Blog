package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.AccessOptions.GetOptions.GetNulls;
import org.springframework.stereotype.Service;

import com.example.demo.NotFoundException;
import com.example.demo.dao.BlogRepository;
import com.example.demo.util.MyBeanUtils;
import com.example.demo.vo.Blog;
import com.example.demo.vo.BlogQuery;
import com.example.demo.vo.Type;

@Service
public class BlogServiceImpl implements BlogService{
	
	@Autowired
	BlogRepository blogrepository;

	@Override
	public Blog getBlog(Long id) {
		// TODO Auto-generated method stub
		return blogrepository.getOne(id);
		
		}

	@Override
	public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
		// TODO Auto-generated method stub
		
		return blogrepository.findAll(new Specification<Blog>() {

			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				
				List<Predicate> predicates = new ArrayList<>();
				if(!"".equals(blog.getTitle()) && blog.getTitle()!=null) {
					predicates.add(criteriaBuilder.like(root.<String>get("title"),"%" + blog.getTitle() + "%"));
				}
				
				//根據分類type 去做查詢
				if(blog.getTypeId()!=null) {
					predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
				}
				if(blog.isRecommand()) {
					predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommand()));
				}
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		
		},pageable);
	}

	@Transactional
	@Override
	public Blog saveBlog(Blog blog) {
		// TODO Auto-generated method stub
		
		if(blog.getId() == 0) {
			blog.setCreateTime(new Date());
			blog.setUpdateTime(new Date());
			blog.setViews(0);
		}else {
			blog.setUpdateTime(new Date());
		}
		
		return blogrepository.save(blog);
	}

	@Transactional
	@Override
	public Blog updateBlog(Long id, Blog blog) {
		// TODO Auto-generated method stub
		
		Blog b = blogrepository.getOne(id);		
		if(b ==null) {
			throw new NotFoundException("該Blog不存在");
		}
		
		BeanUtils.copyProperties(blog,b,MyBeanUtils.getNullPropertyNames(blog));
		b.setUpdateTime(new Date());
		return blogrepository.save(b);
	}

	@Transactional
	@Override
	public void deleteBlog(Long id) {
		// TODO Auto-generated method stub
		blogrepository.deleteById(id);
		
	}

	@Override
	public Page<Blog> listBlog(Pageable pageable) {
		// TODO Auto-generated method stub
		return blogrepository.findAll(pageable);
	}

	@Override
	public List<Blog> listcommentBlogTop(Integer size) {
		// TODO Auto-generated method stub
		
		Pageable pageable = PageRequest.of(0, size, Sort.Direction.DESC, "updateTime");
		
		return blogrepository.findTop(pageable);
	}

	@Override
	public Page<Blog> listBlog(String query,Pageable pageable) {
		// TODO Auto-generated method stub
		return blogrepository.findByQuery(query, pageable);
	}

	@Override
	public Blog getAndConvert(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	//關聯表的查詢　table:tag & blog  多對多
	public Page<Blog> listBlog(Pageable pageable, Long tagId) {
		// TODO Auto-generated method stub
								//關聯查詢
		return blogrepository.findAll(new Specification<Blog>() {

			@Override
			//可以查找Jpa security rar
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Join join = root.join("tags");
				return cb.equal(join.get("id"), tagId);
			}
		},pageable);
		 
	}

	@Override
	public Map<String, List<Blog>> archiveBlog() {
		// TODO Auto-generated method stub
		List<String> years = blogrepository.findGroupYear(); //依照Blog "新增年度" 進行goup by 再排序
		Map<String,List<Blog>> map = new HashMap<>(); // 儲存年度列表
		for(String  year:years) {
			map.put(year,blogrepository.findByYear(year));
		}
		return map;
	}

	@Override
	public Long countBlog() {
		// TODO Auto-generated method stub
		return blogrepository.count();
	}

	/*@Transactional
	@Override
	public Blog getAndConvert(Long id) {
		// TODO Auto-generated method stub
		Blog blog = blogrepository.getOne(id);
		
		if(blog == null) {
			throw new NotFoundException("該Blog不存在");
		}
		Blog b = new Blog();
		BeanUtils.copyProperties(blog, b);
		String content = b.getContent();
		b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
		blogrepository.updateViews(id);
		return b;
		
		
		return null;
	}*/

}
