package com.github.handioq.diber.service.impl;

import com.github.handioq.diber.model.entity.Role;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public boolean hasPermissions(User user, long userId) {
        // if user from @AuthenticationPrincipal id is equal to userId from @PathVariable
        LOGGER.info("hasPermissions with @AuthPrincipal user id: " + user.getId() + " : userId from @PathVariable: " + userId);

        // todo refactor this some small shit and extract "ROLE_ADMIN"!!!
        for (Role role : user.getRoles()) {
            if (role.getName().equalsIgnoreCase("ROLE_ADMIN")) {
                LOGGER.info("detected admin permissions (ROLE_ADMIN)");
                return true;
            }
        }

        return user.getId() == userId;
    }

    @Override
    public boolean hasAdminPermissions(User user) {
        for (Role role : user.getRoles()) {
            if (role.getName().equalsIgnoreCase("ROLE_ADMIN")) {
                LOGGER.info("detected admin permissions (ROLE_ADMIN)");
                return true;
            }
        }

        return false;
    }
}
