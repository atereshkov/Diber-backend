package com.github.handioq.diber.service.impl;

import com.github.handioq.diber.model.entity.Product;
import com.github.handioq.diber.repository.ProductRepository;
import com.github.handioq.diber.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product getById(long id) {
        return productRepository.findOne(id);
    }

    @Override
    public Page<Product> findAllByPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}