package com.github.handioq.diber.repository;

import com.github.handioq.diber.model.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByUserId(long id);

    List<Message> findByTicketId(long id);

    Page<Message> findByUserId(long id, Pageable pageable);

}