package com.springcloud.hystrixservice.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.springcloud.hystrixservice.config.UserResult;
import com.springcloud.hystrixservice.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*import org.springframework.beans.BeanUtils;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
/*import sun.text.normalizer.UBiDiProps;*/

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.user-service}")
    private String userServiceUrl;

    /**
     * 调用方法，fallbackMethod：指定服务降级处理方法
     * @param id
     * @return
     */
    @HystrixCommand(
            fallbackMethod = "getDefaultUser",
            commandKey = "getUserCommand",
            groupKey = "getUserGroup",
            threadPoolKey = "getUserThreadPool"
    )
    public UserResult getUser(Long id){
        return restTemplate.getForObject(userServiceUrl+"/user/{1}",UserResult.class,id);
    }

    /**
     * 服务降级方法
     * @param id
     * @return
     */
    public UserResult getDefaultUser(@PathVariable Long id){
        User user =  new User(-1L,"getDefaultUser","123456");
        return new UserResult(user);
    }

    /**
     * 处理异常方法
     * @param id
     * @return
     */
    @HystrixCommand(
            fallbackMethod = "getDefaultUser2",ignoreExceptions = {NullPointerException.class}
    )
    public UserResult getUserException(@PathVariable Long id){
        if (id ==1){
            throw new IndexOutOfBoundsException();
        }else if (id == 2){
            throw new NullPointerException();
        }
        return restTemplate.getForObject(userServiceUrl+"/user/{1}",UserResult.class,id);
    }

    public UserResult getDefaultUser2(@PathVariable Long id,Throwable e){
        logger.error("getDefaultUser2  id:{},throwable class:{}",id,e.getClass());
        User user = new User(-2L,"getDefaultUser2","123456");
        return new UserResult(user);
    }

    @HystrixCommand(fallbackMethod = "getDefaultUser",
            commandKey = "getUserCommand",
            groupKey = "getUserGroup",
            threadPoolKey = "getUserThreadPool")
    public UserResult getUserCommand(@PathVariable Long id) {
        logger.info("getUserCommand id:{}", id);
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", UserResult.class, id);
    }


    /**
     * 添加具有缓存的方法getUserCache
     * @param id
     * @return
     */
    @CacheResult(cacheKeyMethod = "getCacheKey")    //开启缓存,指定getCacheKey方法作为key
    @HystrixCommand(
            fallbackMethod = "getDefaultUser",
            commandKey = "getUserCache"
    )
    public UserResult getUserCache(Long id){
        logger.info("getUserCache id:{}",id);
        return restTemplate.getForObject(userServiceUrl+"/user/{1}",UserResult.class,id);
    }

    /**
     * 为缓存生成key的方法
     */
    public String getCacheKey(Long id){
        return String.valueOf(id);
    }

    /**
     * 移除缓存方法
     */
    @CacheRemove(commandKey = "getUserCache",cacheKeyMethod = "getCacheKey")
    @HystrixCommand
    public UserResult removeCache(Long id){
        logger.info("removeCacge id:{}",id);
        return restTemplate.getForObject(userServiceUrl+"/user/{1}",UserResult.class,id);
    }

    @HystrixCollapser(batchMethod = "getUserByIds",collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "100")
    })
    public Future<User> getUserFuture(Long id){
        return new AsyncResult<User>(){
            @Override
            public User invoke(){
                UserResult userResult= restTemplate.getForObject(userServiceUrl+"/user/{1}",UserResult.class,id);
                Map data =(Map)userResult.getData();
                User user = BeanUtil.mapToBean(data,User.class,true);
                logger.info("getUserById username:{}",user.getUsername());
                return user;
            }
        };
    }

    @HystrixCommand
    public List<User> getUserByIds(List<Long> ids){
        logger.info("getUserByIds:{}",ids);
        UserResult userResult = restTemplate.getForObject(userServiceUrl+"/user/getUserByIds?ids={1}",UserResult.class, CollUtil.join(ids,","));
        return (List<User>) userResult.getData();
    }

}
