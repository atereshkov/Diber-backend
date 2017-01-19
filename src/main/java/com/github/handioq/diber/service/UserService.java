package com.github.handioq.diber.service;

import com.github.handioq.diber.model.entity.User;

import java.util.List;

public interface UserService {

    User findByUsername(String name);

    User findByEmail(String email);

    User findOne(long id);

    void delete(long id);

    List<User> findAll();

    void saveOrUpdate(User user);

}