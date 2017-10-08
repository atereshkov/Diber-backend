package com.github.handioq.diber.service.impl;

import com.github.handioq.diber.model.entity.Request;
import com.github.handioq.diber.repository.RequestRepository;
import com.github.handioq.diber.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    final
    RequestRepository requestRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public List<Request> findByOrderId(long userId) {
        return requestRepository.findByOrderId(userId);
    }

    @Override
    public void addRequest(Request request) {
        requestRepository.save(request);
    }

    @Override
    public Request findByOrderIdAndCourierId(long orderId, long courierId) {
        return requestRepository.findByOrderIdAndCourierId(orderId, courierId);
    }

    @Override
    public Request getById(long id) {
        return requestRepository.findOne(id);
    }

    @Override
    public void saveOrUpdate(Request request) {
        requestRepository.save(request);
    }

    @Override
    public List<Request> findByCourierId(long userId) {
        return requestRepository.findByCourierId(userId);
    }
}
