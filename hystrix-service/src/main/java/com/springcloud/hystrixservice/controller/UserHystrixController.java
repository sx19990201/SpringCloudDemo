package com.springcloud.hystrixservice.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.springcloud.hystrixservice.config.UserResult;
import com.springcloud.hystrixservice.pojo.User;
import com.springcloud.hystrixservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/user")
public class UserHystrixController {

    @Autowired
    UserService userService;

    /**
     * 用于测试服务降级的接口
     *
     * @param id
     * @return
     */
    @GetMapping("/testFallback/{id}")
    public UserResult testFallback(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/testCommand/{id}")
    public UserResult testCommand(@PathVariable Long id) {
        return userService.getUserCommand(id);
    }

    /**
     * 使用ignoreExcptions忽略某些异常降级
     */
    @GetMapping("/testExcaption/{id}")
    public UserResult testExcaption(@PathVariable Long id) {
        return userService.getUserException(id);
    }

    /**
     * 测试缓存接口
     */
    @GetMapping("/testCache/{id}")
    public UserResult testCache(@PathVariable Long id) {
        userService.getUserCache(id);
        userService.getUserCache(id);
        userService.getUserCache(id);
        return new UserResult("操作成功", 200);
    }

    @GetMapping("/testRemoveCache/{id}")
    public UserResult testRemoveCache(@PathVariable Long id) {
        userService.getUserCache(id);
        userService.removeCache(id);
        userService.getUserCache(id);
        userService.getUserCache(id);
        return new UserResult("操作成功", 200);
    }

    @GetMapping("/testCollapser")
    public UserResult testCollapser() throws ExecutionException, InterruptedException {
        Future<User> future1 = userService.getUserFuture(1L);
        Future<User> future2 = userService.getUserFuture(2L);
        future1.get();
        future2.get();
        ThreadUtil.safeSleep(200);
        Future<User> future3 = userService.getUserFuture(3L);
        future3.get();
        return new UserResult("操作成功", 200);
    }


}
