package org.Chr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.Chr.domain.ResponseResult;
import org.Chr.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-07-01 17:54:58
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
