package com.github.handioq.diber.service;

import com.github.handioq.diber.model.entity.Shop;

public interface ShopService {

    Shop findOne(long id);

    void saveOrUpdate(Shop shop);

}