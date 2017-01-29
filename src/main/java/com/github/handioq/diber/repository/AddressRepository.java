package com.github.handioq.diber.repository;

import com.github.handioq.diber.model.entity.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface AddressRepository extends CrudRepository<Address, Long> {

    List<Address> findByUserId(long id);

}