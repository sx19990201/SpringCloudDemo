package com.springcloud.ribbonserivce.controller;

import com.springcloud.ribbonserivce.config.UserResult;
import com.springcloud.ribbonserivce.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/user")
public class UserRibbonController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.user-service}")
    private String userServiceUrl;

    @GetMapping("/{id}")
    public UserResult getUser(@PathVariable Long id){
        return restTemplate.getForObject(userServiceUrl+"/user/{1}",UserResult.class,id);
    }

    @GetMapping("/getByUsername")
    public UserResult getByUsername(@RequestParam String username) {
        return restTemplate.getForObject(userServiceUrl + "/user/getByUsername?username={1}", UserResult.class, username);
    }

    @GetMapping("/getEntityByUsername")
    public UserResult getEntityByUsername(@RequestParam String username) {
        ResponseEntity<UserResult> entity = restTemplate.getForEntity(userServiceUrl + "/user/getByUsername?username={1}", UserResult.class, username);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new UserResult("操作失败", 500);
        }
    }

    @PostMapping("/create")
    public UserResult create(@RequestBody User user) {
        return restTemplate.postForObject(userServiceUrl + "/user/create", user, UserResult.class);
    }

    @PostMapping("/update")
    public UserResult update(@RequestBody User user) {
        return restTemplate.postForObject(userServiceUrl + "/user/update", user, UserResult.class);
    }

    @PostMapping("/delete/{id}")
    public UserResult delete(@PathVariable Long id) {
        return restTemplate.postForObject(userServiceUrl + "/user/delete/{1}", null, UserResult.class, id);
    }

}
