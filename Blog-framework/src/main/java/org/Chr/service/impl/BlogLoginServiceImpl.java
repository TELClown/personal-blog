package org.Chr.service.impl;

import org.Chr.Utils.BeanCopyUtils;
import org.Chr.Utils.JwtUtil;
import org.Chr.Utils.RedisCache;
import org.Chr.domain.ResponseResult;
import org.Chr.domain.entity.LoginUser;
import org.Chr.domain.entity.User;
import org.Chr.domain.vo.BlogUserLoginVo;
import org.Chr.domain.vo.UserInfoVo;
import org.Chr.service.BlogLoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;


@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);

        //把token和userinfo封装 返回
        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        //解析token 获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long uid = loginUser.getUser().getId();
        //从redis中删除登录信息
        redisCache.deleteObject("bloglogin:"+uid);
        return ResponseResult.okResult();
    }
}
