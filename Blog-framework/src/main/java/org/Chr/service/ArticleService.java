package org.Chr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.Chr.domain.ResponseResult;
import org.Chr.domain.entity.Article;

/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2023-06-30 15:30:18
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);
}

