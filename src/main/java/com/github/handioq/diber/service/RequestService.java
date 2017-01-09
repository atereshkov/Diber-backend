package com.github.handioq.diber.service;

import com.github.handioq.diber.model.entity.Request;

import java.util.List;

public interface RequestService {

    List<Request> findByOrderId(long userId);

}