package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.vo.Tag;

public interface TagService {
	
	Tag saveTag(Tag tag);
	
	Tag getTag(Long id);
	
	Tag getTagByName(String name);
	
	Page<Tag> listTag(Pageable pageable);
	
	List<Tag> listTag();
	
	List<Tag> listTagTop(Integer size);
	
	List<Tag> listTag(String ids);
	
	Tag updateTag(Long id, Tag Type);
	
	void deltetTag(Long id);

}
