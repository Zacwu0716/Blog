package com.example.demo.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.vo.Tag;

public interface TagReporitory extends JpaRepository<Tag,Long>{

	Tag findByName(String name);
	
	@Query("SELECT t from Tag t")
	List<Tag> findTop(Pageable pageable);
}
