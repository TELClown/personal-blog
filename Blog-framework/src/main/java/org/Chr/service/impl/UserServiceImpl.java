package org.Chr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.Chr.Utils.BeanCopyUtils;
import org.Chr.Utils.SecurityUtils;
import org.Chr.domain.Enum.AppHttpCodeEnum;
import org.Chr.domain.ResponseResult;
import org.Chr.domain.entity.User;
import org.Chr.domain.vo.UserInfoVo;
import org.Chr.exception.SystemException;
import org.Chr.mapper.UserMapper;
import org.Chr.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 用户表(User)表服务实现类
 *
 * @author TELClown
 * @since 2023-07-04 15:10:16
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult userInfo() {
        Long userid = SecurityUtils.getUserId();
        User user = userService.getById(userid);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        user.setId(SecurityUtils.getUserId());
        userService.updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //判断用户信息是否为空
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        //对数据进行判断是否存在
        User userExist = userExist(user);
        if(userExist != null){
            if(userExist.getUserName().equals(user.getUserName())){throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);}
            if(userExist.getNickName().equals(user.getNickName())){throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);}
            if(userExist.getEmail().equals(user.getEmail())){throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);}
        }
        //对密码进行加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    private User userExist(User user) {
        User userExist = userMapper.userExist(user);
        System.out.println(userExist);
        if(userExist != null){
            return userExist;
        }
        return null;
    }
}
