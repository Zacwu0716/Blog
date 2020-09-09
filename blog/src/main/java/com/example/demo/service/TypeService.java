package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.vo.Type;

public interface TypeService {

	Type saveType(Type type);
	
	Type getType(long id);
	
	Page<Type> listType(Pageable pageable);
	
	List<Type> ListType();
	
	List<Type> ListTypeTop(Integer size);
	
	Type updateType(Long id,Type type);
	
	void deleteType(Long id);
	
	Type getTypeByName(String name);
	
	
}
