package com.github.handioq.diber.repository;

import com.github.handioq.diber.model.entity.Shop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ShopRepository extends CrudRepository<Shop, Long> {



}
