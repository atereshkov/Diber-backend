package com.github.handioq.diber.repository;

import com.github.handioq.diber.model.entity.Shop;
import com.github.handioq.diber.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ShopRepository extends JpaRepository<Shop, Long> {

    List<Shop> findByUserId(long id);

    Shop findByNameAndUser(String name, User user);

}