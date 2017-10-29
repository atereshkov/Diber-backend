package com.github.handioq.diber.service;

import com.github.handioq.diber.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User findByUsername(String name);

    User findByEmail(String email);

    Page<User> findAllByPage(Pageable pageable);

    User findOne(long id);

    void delete(long id);

    List<User> findAll();

    void saveOrUpdate(User user);

    Long count();

}