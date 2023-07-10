package org.Chr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.Chr.domain.ResponseResult;
import org.Chr.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-07-03 14:22:59
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);

}
