package com.github.handioq.diber.repository;

import com.github.handioq.diber.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Note that this method is not implemented and its working code will be
     * automatically generated from its signature by Spring Data JPA.
     */

    User findByEmail(String email);

    User findByUsername(String username);
}