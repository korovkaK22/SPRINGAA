package com.example.springaa.repositories;

import com.example.springaa.entity.Queue;
import com.example.springaa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Integer> {


    @Query(value = "SELECT q FROM Queue q ORDER BY q.id DESC")
    public List<Queue> getAllQueuesDesc();

    public Optional<Queue> getById();


}
