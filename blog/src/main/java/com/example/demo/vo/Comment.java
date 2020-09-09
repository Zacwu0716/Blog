package com.example.demo.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="t_comment")
public class Comment {
	
	@Id
	@GeneratedValue
	private long id;
	private String nickname;
	private String email;
	private String content;
	private String avatar;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@ManyToOne
	private Blog blog;
	
	@OneToMany(mappedBy="parentComment")
	private List<Comment> replayComments = new ArrayList<>();
	
	@ManyToOne
	private Comment parentComment;
	
	private boolean adminComment;
	
	public Comment() {
		super();
	}
		
	public boolean isAdminComment() {
		return adminComment;
	}



	public void setAdminComment(boolean adminComment) {
		this.adminComment = adminComment;
	}



	public List<Comment> getReplayComments() {
		return replayComments;
	}



	public void setReplayComments(List<Comment> replayComments) {
		this.replayComments = replayComments;
	}



	public Comment getParentComment() {
		return parentComment;
	}



	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}



	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", nickname=" + nickname + ", email=" + email + ", content=" + content
				+ ", avatar=" + avatar + ", createTime=" + createTime + ", blog=" + blog + ", replayComments="
				+ replayComments + ", parentComment=" + parentComment + ", adminComment=" + adminComment + "]";
	}
	
	
	
	

}
