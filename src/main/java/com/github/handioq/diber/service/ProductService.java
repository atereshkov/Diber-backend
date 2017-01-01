package com.github.handioq.diber.service;

import com.github.handioq.diber.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Product getById(long id);

    //List<Product> findAll();

    Page<Product> findAllByPage(Pageable pageable);

}