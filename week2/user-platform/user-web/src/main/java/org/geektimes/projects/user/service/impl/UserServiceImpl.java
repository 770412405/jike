package org.geektimes.projects.user.service.impl;

import org.geektimes.projects.user.domain.User;

import org.geektimes.projects.user.service.UserService;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.List;

import java.util.Set;
import java.util.logging.Logger;

/**
 * @author: 肖震
 * @date: 2021/3/3
 * @since:
 */
public class UserServiceImpl implements UserService {
    private static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Resource(name="bean/EntityManager")
    private EntityManager entityManager;
    @Resource(name="bean/Validator")
    private Validator validator;


    @Override
    public User queryUserById(Long id) {
        return entityManager.find(User.class,id);
    }

    @Override
    public boolean saveUser(User user) {
        try {
            validateParams(user);
            entityManager.persist(user);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void validateParams(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        String message ="";
        if(!violations.isEmpty()){
            for(ConstraintViolation<User> v:violations){
                logger.info(v.getMessage());
                message+=v.getMessage();
            }
            throw  new RuntimeException(message);
        }
    }

    @Override
    public List<User> queryAll() {
        String hql = " from User";
        Query query = entityManager.createQuery(hql);
        List<User> users = query.getResultList();
        return users;
    }
}
