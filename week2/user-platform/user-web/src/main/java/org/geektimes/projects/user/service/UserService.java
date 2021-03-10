package org.geektimes.projects.user.service;

import org.geektimes.projects.user.domain.User;

import java.util.List;

/**
 * 用户服务
 */
public interface UserService {

    public User queryUserById(Long id);

    public boolean saveUser(User user);

    public List<User> queryAll();
}
