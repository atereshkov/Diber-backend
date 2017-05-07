package com.github.handioq.diber.service.impl;

import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.service.SecurityService;
import com.github.handioq.diber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    UserService userService;

    @Override
    public boolean hasPermissions(User user, long userId) {
        // if user from @AuthenticationPrincipal id is equal to userId from @PathVariable
        return user.getId() == userId;
    }
}
