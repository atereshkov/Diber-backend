package com.github.handioq.diber.service.impl;

import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.repository.UserRepository;
import com.github.handioq.diber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Page<User> findAllByPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findOne(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public void delete(long id) {
        userRepository.delete(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void saveOrUpdate(User user) {
        userRepository.save(user);
    }

    @Override
    public Long count() {
        return userRepository.count();
    }
}