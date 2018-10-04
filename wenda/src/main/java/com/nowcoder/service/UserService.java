package com.nowcoder.service;

import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserDAO userDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

}
