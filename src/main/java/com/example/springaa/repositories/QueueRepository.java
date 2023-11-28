package com.example.springaa.repositories;

import com.example.springaa.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QueueRepository extends JpaRepository<Queue, Integer> {

    @Query("SELECT q FROM Queue q WHERE q.id IN :ids ORDER BY q.id DESC")
    public List<Queue> getAllQueueByIds(List<Integer> ids);

}
