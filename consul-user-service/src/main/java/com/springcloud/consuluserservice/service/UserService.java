package com.springcloud.consuluserservice.service;

import com.springcloud.consuluserservice.pojo.User;

import java.util.List;

public interface UserService {
    //根据id获取用户
    User getUser(Long id);

    //根据多个id获取用户集合
    List<User> getUserByIds(List<Long> ids);

    //添加用户
    void create(User user);

    //修改
    void update(User user);
    //删除
    void delete(Long id);

    //根据name获取用户
    User getByUsername(String username);
}
