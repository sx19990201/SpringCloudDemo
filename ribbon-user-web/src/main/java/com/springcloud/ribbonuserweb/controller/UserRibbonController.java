package com.springcloud.ribbonuserweb.controller;


import com.springcloud.ribbonuserweb.pojo.User;
import com.springcloud.ribbonuserweb.result.UserResult;
import com.springcloud.ribbonuserweb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRibbonController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    public UserResult<User> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        LOGGER.info("根据id获取用户信息，用户名称为：{}",user.getUsername());
        return new UserResult<>(user);
    }

    @GetMapping("/getUserByIds")
    public UserResult<List<User>> getUserByIds(@RequestParam List<Long> ids) {
        List<User> userList= userService.getUserByIds(ids);
        LOGGER.info("根据ids获取用户信息，用户列表为：{}",userList);
        return new UserResult<>(userList);
    }

    @PostMapping("/create")
    public UserResult create(@RequestBody User user) {
        userService.create(user);
        return new UserResult("操作成功", 200);
    }

    @GetMapping("/getByUsername")
    public UserResult<User> getByUsername(@RequestParam String username) {
        User user = userService.getByUsername(username);
        return new UserResult<>(user);
    }

    @PostMapping("/update")
    public UserResult update(@RequestBody User user) {
        userService.update(user);
        return new UserResult("操作成功", 200);
    }

    @PostMapping("/delete/{id}")
    public UserResult delete(@PathVariable Long id) {
        userService.delete(id);
        return new UserResult("操作成功", 200);
    }

}
