package org.Chr.service;

import org.Chr.domain.ResponseResult;
import org.Chr.domain.entity.User;

public interface BlogLoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
