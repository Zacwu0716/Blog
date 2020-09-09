package com.example.demo.vo;

//用來做分頁查詢
public class BlogQuery {
	
	private String title;
	
	private Long typeId;
	
	private boolean recommand;

	public BlogQuery() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public boolean isRecommand() {
		return recommand;
	}

	public void setRecommand(boolean recommand) {
		this.recommand = recommand;
	}


	
	

}
