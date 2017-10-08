package com.github.handioq.diber.service.impl;

import com.github.handioq.diber.model.entity.Shop;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.repository.ShopRepository;
import com.github.handioq.diber.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    private final
    ShopRepository shopRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public Shop findOne(long id) {
        return shopRepository.findOne(id);
    }

    @Override
    public void saveOrUpdate(Shop shop) {
        shopRepository.save(shop);
    }

    @Override
    public List<Shop> findByUserId(long userId) {
        return shopRepository.findByUserId(userId);
    }

    @Override
    public Shop findByNameAndUser(String name, User user) {
        return shopRepository.findByNameAndUser(name, user);
    }

    @Override
    public void delete(long shopId) {
        shopRepository.delete(shopId);
    }
}