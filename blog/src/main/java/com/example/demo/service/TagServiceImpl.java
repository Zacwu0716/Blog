package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.NotFoundException;
import com.example.demo.dao.TagReporitory;
import com.example.demo.vo.Tag;
@Service
public class TagServiceImpl implements TagService{
	
	@Autowired
	private TagReporitory tagrepository;

	@Override
	public Tag saveTag(Tag tag) {
		// TODO Auto-generated method stub
		
		
		return tagrepository.save(tag);
	}

	@Override
	public Tag getTag(Long id) {
		// TODO Auto-generated method stub
		return tagrepository.getOne(id);
	}

	@Override
	public Tag getTagByName(String name) {
		// TODO Auto-generated method stub
		return tagrepository.findByName(name);
	}

	@Override
	public Page<Tag> listTag(Pageable pageable) {
		// TODO Auto-generated method stub
		return tagrepository.findAll(pageable);
	}

	@Override
	public List<Tag> listTag() {
		// TODO Auto-generated method stub
		return tagrepository.findAll();
	}

	@Override
	public List<Tag> listTag(String ids) {
		// TODO Auto-generated method stub
		
		return tagrepository.findAllById(convertToList(ids));
	}
	
	
	private List<Long> convertToList(String ids){
		List<Long> list = new ArrayList<>();
		if(!"".equals(ids) && ids!=null) {
			String[] idarry = ids.split(",");
			for(int i=0; i<idarry.length;i++) {
				list.add(new Long(idarry[i]));
			}
		}
		return list;
	}

	@Transactional
	@Override
	public Tag updateTag(Long id, Tag tag) {
		// TODO Auto-generated method stub
		Tag t = tagrepository.getOne(id);
		if(t== null) {
			throw new NotFoundException("不存在該標籤");
		}
		BeanUtils.copyProperties(tag, t);
		return tagrepository.save(t);
	}
	
	@Transactional
	@Override
	public void deltetTag(Long id) {
		// TODO Auto-generated method stub
		tagrepository.deleteById(id);
	}

	@Override
	public List<Tag> listTagTop(Integer size) {
		// TODO Auto-generated method stub
		
		Pageable pageable = PageRequest.of(0, size, Sort.Direction.DESC, "blogs.size");
		
		return tagrepository.findTop(pageable);
	}

	

}
