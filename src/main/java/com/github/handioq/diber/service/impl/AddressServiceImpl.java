package com.github.handioq.diber.service.impl;

import com.github.handioq.diber.model.entity.Address;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.repository.AddressRepository;
import com.github.handioq.diber.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Address findOne(long addressId) {
        return addressRepository.findOne(addressId);
    }

    @Override
    public Page<Address> findAllByPage(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    @Override
    public Page<Address> findByUserId(long userId, Pageable pageable) {
        return addressRepository.findByUserId(userId, pageable);
    }

    @Override
    public Long count() {
        return addressRepository.count();
    }

    @Override
    public void delete(long addressId) {
        addressRepository.delete(addressId);
    }

    @Override
    public Long countByUserId(long userId) {
        return addressRepository.countByUserId(userId);
    }
}
