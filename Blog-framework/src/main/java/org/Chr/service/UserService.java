package org.Chr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.Chr.domain.ResponseResult;
import org.Chr.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author TELClown
 * @since 2023-07-04 15:10:15
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

}
