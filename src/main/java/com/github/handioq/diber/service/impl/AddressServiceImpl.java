package com.github.handioq.diber.service.impl;

import com.github.handioq.diber.model.entity.Address;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.repository.AddressRepository;
import com.github.handioq.diber.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> findByUserId(long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    public void saveOrUpdate(Address address) {
        addressRepository.save(address);
    }

    @Override
    public Address findByName(String name) {
        return addressRepository.findByName(name);
    }

    @Override
    public Address findByNameAndUser(String name, User user) {
        return addressRepository.findByNameAndUser(name, user);
    }
}
