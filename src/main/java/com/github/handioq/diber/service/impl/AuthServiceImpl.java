package com.github.handioq.diber.service.impl;

import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.repository.AuthRepository;
import com.github.handioq.diber.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Override
    public void register(User user) {
        authRepository.save(user);
    }

}
