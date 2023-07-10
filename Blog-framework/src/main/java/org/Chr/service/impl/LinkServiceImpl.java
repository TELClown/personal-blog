package org.Chr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.Chr.Utils.BeanCopyUtils;
import org.Chr.constants.SystemConstants;
import org.Chr.domain.ResponseResult;
import org.Chr.domain.entity.Link;
import org.Chr.domain.vo.AllLinkVo;
import org.Chr.mapper.LinkMapper;
import org.Chr.service.LinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-07-01 17:54:58
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Resource
    LinkService linkService;
    @Override
    public ResponseResult getAllLink() {
        //查询所有友链
        LambdaQueryWrapper<Link> linkWrapper = new LambdaQueryWrapper<>();
        linkWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> linkList = list(linkWrapper);
        //封装VO
        List<AllLinkVo> allLinkVos = BeanCopyUtils.copyBeanList(linkList, AllLinkVo.class);
        return ResponseResult.okResult(allLinkVos);
    }
}
