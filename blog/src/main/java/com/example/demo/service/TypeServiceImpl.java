package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.NotFoundException;
import com.example.demo.dao.TypeRepository;
import com.example.demo.vo.Type;

@Service
public class TypeServiceImpl implements TypeService{
	
	@Autowired
	TypeRepository typerepository;

	@Override
	@Transactional
	public Type saveType(Type type) {
		// TODO Auto-generated method stub
		return typerepository.save(type);
	}

	@Override
	@Transactional
	public Type getType(long id) {
		// TODO Auto-generated method stub
		return typerepository.getOne(id);
	}

	@Override
	@Transactional
	public Page<Type> listType(Pageable pageable) {
		// TODO Auto-generated method stub
		return typerepository.findAll(pageable);
	}

	@Override
	@Transactional
	public Type updateType(Long id, Type type) {
		// TODO Auto-generated method stub
		Optional<Type> t = typerepository.findById(id);
		Type tmp = t.get();
		if(tmp == null) {
			throw new NotFoundException("不存在該類型");
		}
		BeanUtils.copyProperties(type, t);
		return typerepository.save(type);
	}

	@Override
	@Transactional
	public void deleteType(Long id) {
		// TODO Auto-generated method stub
		typerepository.deleteById(id);
	}

	public Type getTypeByName(String name) {
		// TODO Auto-generated method stub
		return typerepository.findByName(name);
	}
	
	@Override
	public List<Type> ListType() {
		// TODO Auto-generated method stub
		return typerepository.findAll();
	}

	@Override
	public List<Type> ListTypeTop(Integer size) {
		// TODO Auto-generated method stub
		
		Pageable pageable = PageRequest.of(0, size, Sort.Direction.DESC, "blogs.size");
		return typerepository.findTop(pageable);
	}

}
