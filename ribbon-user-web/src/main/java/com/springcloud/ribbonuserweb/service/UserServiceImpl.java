package com.springcloud.ribbonuserweb.service;

import com.springcloud.ribbonuserweb.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private List<User> userList;

    /**
     * 初始化集合
     */
    @PostConstruct
    public void initData() {
        userList = new ArrayList<>();
        userList.add(new User(1L, "admin1", "123456"));
        userList.add(new User(2L, "admin2", "123456"));
        userList.add(new User(3L, "admin3", "123456"));
    }

    /**
     * 通过id找到对象
     * @param id
     * @return
     */
    @Override
    public User getUser(Long id) {
        //java8新特性，Lambda 表达式，编译器会自动推导出参数类型，
        //比如下面的userItem 会自动推导出List类
        List<User> findUserList = userList.stream().
                filter(userItem -> userItem.getId().equals(id)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(findUserList)) {
            return findUserList.get(0);
        }
        return null;
    }

    @Override
    public List<User> getUserByIds(List<Long> ids) {
        List<User> findUserByIds = userList.stream().filter(userItem -> ids.contains(userItem.getId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(findUserByIds)) {
            return findUserByIds;
        }
        return null;
    }

    @Override
    public void create(User user) {
        userList.add(user);
    }

    @Override
    public void update(User user) {
        userList.stream().filter(userItem->userItem.getId().equals(user.getId())).forEach(userItem->{
            userItem.setUsername(user.getUsername());
            userItem.setPasswork(user.getPasswork());
                });
    }

    @Override
    public void delete(Long id) {
        User user =getUser(id);
        if (user!=null){
            userList.remove(user);
        }
    }

    @Override
    public User getByUsername(String username) {
      /*  List<User> findUserName = new ArrayList<>();
        for (User user : userList){
            if (username.equals(user.getUsername())){
                findUserName.add(user);
            }
        }
        if(findUserName!=null){
            return findUserName.get(0);
        }
        return null;*/
        List<User> findUserName = userList.stream().filter
                (userItem->userItem.getUsername().equals(username)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(findUserName)){
            return findUserName.get(0);
        }
        return null;
    }


}
