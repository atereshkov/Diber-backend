package com.github.handioq.diber.service;

import com.github.handioq.diber.model.entity.Address;
import com.github.handioq.diber.model.entity.User;

import java.util.List;

public interface AddressService {

    List<Address> findByUserId(long userId);

    void saveOrUpdate(Address address);

    Address findByName(String name);

    Address findByNameAndUser(String name, User user);

}