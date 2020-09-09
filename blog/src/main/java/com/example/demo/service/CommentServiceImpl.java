package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CommentRepository;
import com.example.demo.vo.Comment;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentrepository;

	@Override
	// 取得第一層數據列表
	public List<Comment> listCommentByBlogId(Long BolgId) {

		// TODO Auto-generated method stub
		// 取得最高級（第一層）的評論 parentComment為 null
		List<Comment> comments = commentrepository.findByBlogIdAndParentCommentNull(BolgId, Sort.by(Sort.Direction.DESC, "createTime"));
		return eachComment(comments);
	}

	// 最高級（第一層）的評論儲存到另一個List 預防資料因為修改連同資料庫被更動
	private List<Comment> eachComment(List<Comment> comments) {
		List<Comment> commentsView = new ArrayList<>();

		for (Comment comment : comments) {
			Comment c = new Comment();
			BeanUtils.copyProperties(comment, c);
			commentsView.add(c);
		}

		// 處理其他評論層級跟最高級（第一層級）評論的關係
		combineChildren(commentsView);
		return commentsView;
	}

	// 處理其他評論層級跟最高級（第一層級）評論的關係
	// root 節點 blog不為空的對象集合
	private void combineChildren(List<Comment> comments) {

		for (Comment comment : comments) {
			// 最高層級的下一層級回復評論
			// ReplayComment 子代的資訊
			List<Comment> replys = comment.getReplayComments();

			// 得到下一層級的子代資訊, 存放到tempReplys 裡
			for (Comment reply1 : replys) {

				recursively(reply1);
			}
			
			comment.setReplayComments(tempReplys);
			tempReplys = new ArrayList<>();

		}

	}

	// 儲存Iterator後所有的子代集合
	//最後產稱的是只有跟最上層有關係的其下層關係容器
	private List<Comment> tempReplys = new ArrayList<>();

	//一值重複取得子代的資訊
	private void recursively(Comment comment) {
		tempReplys.add(comment);
		if (comment.getReplayComments().size() > 0) {
			List<Comment> replys = comment.getReplayComments();
			for (Comment reply : replys) {
				tempReplys.add(reply);
				if (reply.getReplayComments().size() > 0) {
					recursively(reply);
				}
			}
		}
	}

	@Transactional
	@Override
	public Comment saveComment(Comment comment) {
		// TODO Auto-generated method stub

		Long parentCommentId = comment.getParentComment().getId();
		// 回復評論
		// 父級有值
		if (parentCommentId != -1) {
			comment.setParentComment(commentrepository.getOne(parentCommentId)); // 找到最高級的評論
			// 保存評論
		} else {
			comment.setParentComment(null);
		}
		comment.setCreateTime(new Date());
		return commentrepository.save(comment);
	}

}
