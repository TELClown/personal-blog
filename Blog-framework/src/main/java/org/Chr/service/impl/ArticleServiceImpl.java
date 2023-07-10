package org.Chr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.Chr.Utils.BeanCopyUtils;
import org.Chr.Utils.RedisCache;
import org.Chr.domain.ResponseResult;
import org.Chr.constants.SystemConstants;
import org.Chr.domain.entity.Article;
import org.Chr.domain.entity.Category;
import org.Chr.domain.vo.*;
import org.Chr.mapper.ArticleMapper;
import org.Chr.service.ArticleService;
import org.Chr.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-06-30 15:30:19
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    CategoryService categoryService;

    @Resource
    ArticleMapper articleMapper;
    @Resource
    RedisCache redisCache;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门列表，返回ResponseResult
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须为正式文字
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //文章按照浏览量降序排序
        queryWrapper.orderByDesc(Article::getViewCount);

        Page<Article> page = new Page<>(SystemConstants.PAGE_CURRENT,SystemConstants.PAGE_SIZE);
        page(page,queryWrapper);

        List<Article> articleList = page.getRecords();
        List<HotArticleVo> hotArticleVoList = BeanCopyUtils.copyBeanList(articleList, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVoList);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> articleListWrapper = new LambdaQueryWrapper<>();
        //有分类id传入时，根据id返回对应的文章
        articleListWrapper.eq(Objects.nonNull(categoryId) && categoryId>0, Article::getCategoryId,categoryId);
        //状态是正常的
        articleListWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop进行降序
        articleListWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,articleListWrapper);

        List<Article> articleList = page.getRecords();
        //使用stream时需要在article实体类中加上@Accessors(chain = true) 使得set方法能够返回，而不是void
//        articleList.stream().map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName())).collect(Collectors.toList());
        articleList.forEach(article -> {
            article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
            Integer viewCount = redisCache.getCacheMapValue("article:viewCount", article.getId().toString());
            article.setViewCount(viewCount.longValue());
        });

        //封装vo
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //查询文章
        Article article = getById(id);
        //从redis读取浏览量
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //封装vo
        ArticleDetail articleDetail = BeanCopyUtils.copyBean(article, ArticleDetail.class);
        //根据分类id查询分类名
        Category category = categoryService.getById(articleDetail.getCategoryId());
        if(category != null){
            articleDetail.setCategoryName(category.getName());
        }
        //响应返回
        return ResponseResult.okResult(articleDetail);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }
}

