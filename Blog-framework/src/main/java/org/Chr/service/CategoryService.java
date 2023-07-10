package org.Chr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.Chr.domain.ResponseResult;
import org.Chr.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-07-01 13:43:02
 */
public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();
}
