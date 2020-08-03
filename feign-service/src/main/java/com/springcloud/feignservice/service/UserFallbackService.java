package com.springcloud.feignservice.service;

import com.springcloud.feignservice.config.UserResult;
import com.springcloud.feignservice.pojo.User;
import org.springframework.stereotype.Component;

@Component
public class UserFallbackService implements UserService {
    @Override
    public UserResult<User> getUser(Long id) {
        User defaultUser = new User(-1L,"defaultUser","123456");
        return new UserResult<>(defaultUser);
    }

    @Override
    public UserResult getByUsername(String username) {
        User defaultUser = new User(-1L,"defaultUser","123456");
        return new UserResult<>(defaultUser);
    }

    @Override
    public UserResult create(User user) {
        User defaultUser = new User(-1L,"defaultUser","123456");
        return new UserResult<>(defaultUser);
    }

    @Override
    public UserResult update(User user) {
        return new UserResult("调用失败，服务被降级",500);
    }

    @Override
    public UserResult delete(Long id) {
        return new UserResult("调用失败，服务被降级",500);
    }
}
