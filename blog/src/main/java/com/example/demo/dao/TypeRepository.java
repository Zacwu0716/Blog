package com.example.demo.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.vo.Type;

public interface TypeRepository extends JpaRepository<Type,Long> {

	 Type findByName(String name);
	 
	 @Query("select a from Type a")
	 List<Type> findTop(Pageable pageable);
}
