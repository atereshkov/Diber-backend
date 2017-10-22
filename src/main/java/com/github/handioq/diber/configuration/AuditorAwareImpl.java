package com.github.handioq.diber.configuration;

import com.github.handioq.diber.model.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Long getCurrentAuditor() {
        // Can use Spring Security to return currently logged in user
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

}