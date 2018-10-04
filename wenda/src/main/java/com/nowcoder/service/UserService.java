package com.nowcoder.service;

import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserDAO userDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public Map<String, String> register(String username, String password){
        Map<String, String> map = new HashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msg", "用户名不能为空！");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msg", "密码不能为空！");
            return map;
        }
        User user = userDAO.selectByName(username);
        if(user != null){
            map.put("msg", "该用户名已注册！");
            return map;
        }
        User user1 = new User();
        user1.setName(username);
        user1.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user1.setPassword(password + user1.getSalt());
        user1.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        userDAO.addUser(user1);
        return map;
    }

    public Map<String, String> login(String username, String password){
        Map<String, String> map = new HashMap<>();

        return map;
    }

}
