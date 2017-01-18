package com.github.handioq.diber.service;

import com.github.handioq.diber.model.entity.Address;

import java.util.List;

public interface AddressService {

    List<Address> findByUserId(long userId);

}