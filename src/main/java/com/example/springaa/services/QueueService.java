package com.example.springaa.services;


import com.example.springaa.entity.Queue;
import com.example.springaa.entity.User;
import com.example.springaa.repositories.JDBCQueueRepository;
import com.example.springaa.repositories.QueueRepository;
import com.example.springaa.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class QueueService {

    private final QueueRepository queueRepository;
    private final JDBCQueueRepository jdbcQueueRepository;
    private final UserRepository userRepository;

    public Optional<Queue> getQueueById(int id) {
        return queueRepository.findById(id);
    }

    public boolean isUserInQueue(int queueId, int userId) {
        return getAllUsersInQueue(queueId).stream().anyMatch(u -> u.getId() == userId);
    }

    public List<User> getAllUsersInQueue(int queueId){
        List<Integer> usersId = jdbcQueueRepository.getAllUsersIdInQueue(queueId);
        List<User> result = new LinkedList<>();
        for (int id : usersId){
            Optional<User> userOpt = userRepository.findById(id);
            userOpt.ifPresent(result::add);
        }
        return result;
    }

    public boolean changeCloseable(int id, boolean value){
        Optional<Queue> queueOpt = queueRepository.findById(id);
        if (queueOpt.isPresent()){
           queueOpt.get().setOpen(value);
           return true;
        }
        return false;
    }


    /**
     * Додати користувача у чергу
     * - можна вказувати неіснуючі черги
     * - враховує, чи відкрита черга
     * - враховує, чи існує юзер
     * - не додасть юзера, якщо він вже є в черзі
     * @param queueId id черги, в яку треба додати
     * @param userId id юзера для додавання
     * @return місце користувача або -1, при незнайденні місця
     */
    public boolean addUserToQueue(int queueId, int userId){
        Optional<Queue> queueOpt = queueRepository.findById(queueId);
        Optional<User> userOpt = userRepository.findById(userId);
        return queueOpt.isPresent() &&
                userOpt.isPresent() &&
                queueOpt.get().getIsOpen() &&
                !jdbcQueueRepository.isUserInQueue(queueId,userId) &&
                jdbcQueueRepository.addUserToQueue(queueId, userId);
    }


    public boolean deleteUserFromQueue(int queueId, int userId){
        return jdbcQueueRepository.deleteUserFromQueue(queueId,userId);
    }




}
