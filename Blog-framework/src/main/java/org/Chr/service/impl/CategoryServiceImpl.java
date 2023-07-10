package org.Chr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.Chr.Utils.BeanCopyUtils;
import org.Chr.constants.SystemConstants;
import org.Chr.domain.ResponseResult;
import org.Chr.domain.entity.Article;
import org.Chr.domain.entity.Category;
import org.Chr.mapper.CategoryMapper;
import org.Chr.service.ArticleService;
import org.Chr.service.CategoryService;
import org.Chr.domain.vo.CategoryListVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-07-01 13:43:02
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //查询所有已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);

        //根据查询结果，将分类id去重
        Set<Long> categoryIds = articleList.stream().map(article -> article.getCategoryId()).collect(Collectors.toSet());
        //过滤出文章状态为正常的记录
        List<Category> categoryList = listByIds(categoryIds)
                .stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryListVo> categoryListVos = BeanCopyUtils.copyBeanList(categoryList, CategoryListVo.class);

        return ResponseResult.okResult(categoryListVos);
    }
}
