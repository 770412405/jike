package org.geektimes.projects.user.service.impl;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.repository.UserRepository;
import org.geektimes.projects.user.repository.impl.UserRepositoryImpl;
import org.geektimes.projects.user.service.UserService;

import java.util.List;

/**
 * @author: 肖震
 * @date: 2021/3/3
 * @since:
 */
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;


    public UserServiceImpl(){
        userRepository = new UserRepositoryImpl();
    }

    @Override
    public boolean register(User user) {
        if (queryUserByName(user.getName()) != null) {
            return false;
        }
        return userRepository.save(user);
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User queryUserById(Long id) {
        return null;
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return null;
    }

    @Override
    public User queryUserByName(String name) {
        return userRepository.getByName(name);
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
