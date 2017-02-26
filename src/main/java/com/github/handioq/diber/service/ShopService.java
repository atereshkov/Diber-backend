package com.github.handioq.diber.service;

import com.github.handioq.diber.model.entity.Shop;
import com.github.handioq.diber.model.entity.User;

import java.util.List;

public interface ShopService {

    Shop findOne(long id);

    List<Shop> findByUserId(long userId);

    void saveOrUpdate(Shop shop);

    Shop findByNameAndUser(String name, User user);

}