package com.github.handioq.diber.service.impl;

import com.github.handioq.diber.model.entity.Shop;
import com.github.handioq.diber.repository.ShopRepository;
import com.github.handioq.diber.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopRepository shopRepository;

    @Override
    public Shop findOne(long id) {
        return shopRepository.findOne(id);
    }

    @Override
    public void saveOrUpdate(Shop shop) {
        shopRepository.save(shop);
    }
}