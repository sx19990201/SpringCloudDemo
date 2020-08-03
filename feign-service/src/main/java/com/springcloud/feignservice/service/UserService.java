package com.springcloud.feignservice.service;

import com.springcloud.feignservice.config.UserResult;
import com.springcloud.feignservice.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 添加接口完成对ribbon-user-web服务的接口绑定
 * fallback  设置服务降级处理类为UserFallbackService类
 */
@FeignClient(value = "ribbon-user-web",fallback = UserFallbackService.class)
public interface UserService {

    @GetMapping("/user/{id}")
    UserResult<User> getUser(@PathVariable Long id);

    @GetMapping("/getByUsername")
    UserResult getByUsername(@RequestParam String username);

    @PostMapping("/user/create")
    UserResult create(@RequestBody User user);



    @PostMapping("/user/update")
    UserResult update(@RequestBody User user);

    @PostMapping("/user/delete/{id}")
    UserResult delete(@PathVariable Long id);

}
