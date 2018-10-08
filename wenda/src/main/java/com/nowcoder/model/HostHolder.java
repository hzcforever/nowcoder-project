package com.nowcoder.model;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    //为每一个线程都分配了一个对象
    private static ThreadLocal<User> users = new ThreadLocal<>();
    public User getUser() {
        return users.get();
    }
    public void setUser(User user) {
        users.set(user);
    }
    public void clear() {
        users.remove();
    }

}
