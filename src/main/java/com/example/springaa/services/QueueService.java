package com.example.springaa.services;


import com.example.springaa.entity.Queue;
import com.example.springaa.repositories.QueueRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class QueueService {
    private final QueueRepository queueRepository;

    public Optional<Queue> getQueueById(int id){
        return queueRepository.findById(id);
    }


}
