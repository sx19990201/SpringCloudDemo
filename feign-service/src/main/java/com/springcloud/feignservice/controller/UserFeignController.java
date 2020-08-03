package com.springcloud.feignservice.controller;

import com.springcloud.feignservice.config.UserResult;
import com.springcloud.feignservice.pojo.User;
import com.springcloud.feignservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserFeignController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserResult getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @GetMapping("/getByUsername")
    public UserResult getByUsername(@RequestParam String username) {
        return userService.getByUsername(username);
    }

    @PostMapping("/create")
    public UserResult create(@RequestBody User user) {
        return userService.create(user);
    }

    @PostMapping("/update")
    public UserResult update(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/delete/{id}")
    public UserResult delete(@PathVariable Long id) {
        return userService.delete(id);
    }


}
