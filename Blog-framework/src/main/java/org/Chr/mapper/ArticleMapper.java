package org.Chr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.Chr.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 文章表(Article)表数据库访问层
 *
 * @author TELClown
 * @since 2023-06-30 15:30:15
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    //使用多个参数时需要@Param参数指定参数名，否则会报错
    @Update({"Update sg_article set view_count = #{count} where id = #{id}"})
    Integer updateViewCount(@Param("id")Long id, @Param("count")Long count);
}

