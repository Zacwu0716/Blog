package com.example.demo.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="t_tag")
public class Tag {
	
	@Id
	@GeneratedValue
	private long id;
	private String name;
	
	@ManyToMany(mappedBy = "tags")
	private List<Blog> blogs = new ArrayList<>();
	
	public Tag() {
		super();
	}
	
	
	public List<Blog> getBlogs() {
		return blogs;
	}


	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + "]";
	}
	
	
}
