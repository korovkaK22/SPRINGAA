package com.example.springaa.services;

import com.example.springaa.entity.Queue;
import com.example.springaa.repositories.JDBCQueueRepository;
import com.example.springaa.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HomeServices {
    private JDBCQueueRepository jdbcQueueRepository;

    public List<Queue> findLastQueues(int amount) {
        return  jdbcQueueRepository.findLastQueues(amount);
    }


}
