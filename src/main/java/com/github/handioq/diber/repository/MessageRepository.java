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

    List<Message> findAllByUserId(long id);

    List<Message> findAllByTicketId(long id);

    Page<Message> findAllByUserId(long id, Pageable pageable);

}