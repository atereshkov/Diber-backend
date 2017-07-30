package com.github.handioq.diber.service;

import com.github.handioq.diber.model.entity.User;

public interface SecurityService {

    boolean hasPermissions(User user, long userId);
    boolean hasAdminPermissions(User user);

}