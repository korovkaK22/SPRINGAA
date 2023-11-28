package com.example.springaa.services;

import com.example.springaa.entity.Queue;
import com.example.springaa.repositories.JDBCQueueRepository;
import com.example.springaa.repositories.QueueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HomeServices {
    private JDBCQueueRepository jdbcQueueRepository;
    private QueueRepository queueRepository;

    public List<Queue> findLastQueues(int amount) {
        return  queueRepository.getAllQueueByIds(jdbcQueueRepository.findLastQueueIds(amount));
    }


}
