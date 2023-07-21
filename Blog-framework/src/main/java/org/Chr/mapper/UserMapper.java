package org.Chr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.Chr.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 用户表(User)表数据库访问层
 *
 * @author TELClown
 * @since 2023-07-02 14:06:37
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select({"select user_name,nick_name,email from sys_user where user_name = #{userName} or nick_name = #{nickName} or email = #{email}"})
    User userExist(User user);

}

