package com.example.springaa.services;

import com.example.springaa.entity.Queue;
import com.example.springaa.exceptions.ResourceNotFoundException;
import com.example.springaa.repositories.JDBCQueueRepositoryImpl;
import com.example.springaa.repositories.QueueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HomeServices {
    private JDBCQueueRepositoryImpl jdbcQueueRepository;
    private QueueRepository queueRepository;

    public List<Queue> findLastQueues(int amount) {
        return queueRepository.getAllQueueByIds(jdbcQueueRepository.findLastQueueIds(amount));
    }


}
