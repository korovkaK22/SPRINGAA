package com.example.springaa.services;


import com.example.springaa.entity.Queue;
import com.example.springaa.web.dto.QueueResponse;
import com.example.springaa.entity.User;
import com.example.springaa.repositories.JDBCQueueRepositoryImpl;
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
    private final JDBCQueueRepositoryImpl jdbcQueueRepository;
    private final UserRepository userRepository;

    public Optional<Queue> getQueueById(int id) {
        return queueRepository.findById(id);
    }

    public Optional<Queue> updateName(int id, String name) {
        Optional<Queue> queueOpt = queueRepository.findById(id);
        queueOpt.ifPresent(queue -> queue.setName(name));
        return queueOpt;
    }

    public boolean isUserInQueue(int queueId, int userId) {
        return getAllUsersInQueue(queueId).stream().anyMatch(u -> u.getId() == userId);
    }

    public List<Queue> getAllQueuesByUser(int userId) {
       return queueRepository.findByOwnerId(userId);
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



    public boolean deleteQueue(int id){
        return jdbcQueueRepository.delete(id);
    }


    /**
     * Додати користувача у чергу
     * - можна вказувати неіснуючі черги
     * - враховує, чи відкрита черга
     * - враховує, чи існує юзер
     * - не додасть юзера, якщо він вже є в черзі
     * @param queueId id черги, в яку треба додати
     * @param userId id юзера для додавання
     * @return мчи добавило юзера
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

    public boolean moveQueue(int queueId){
        return deleteUserFromPositionInQueue(queueId, 1);
    }

    public boolean deleteUserFromPositionInQueue(int queueId, int position){
        Optional<Integer> userId = jdbcQueueRepository.getUserIdOnPositionInQueue(queueId, position);
        if (userId.isEmpty()){
            return false;
        }
        return deleteUserFromQueue(queueId, userId.get());
    }


    /**
     * Видаляє юзера з черги, при цьому зміщуючи всі номера наступних людей
     * @param queueId id черги
     * @param userId  id юзера
     * @return Чи було видалено користувача з черги
     */
    public boolean deleteUserFromQueue(int queueId, int userId){
        return jdbcQueueRepository.deleteUserFromQueue(queueId,userId);
    }


    /**
     * Створює нову чергу
     * @param name (ім'я черги)
     * @param userId юзер (існуючий)
     * @return створена черга
     */
    public QueueResponse createQueue(String name, int userId){
       Optional<User> userOpt = userRepository.findById(userId);

       Queue result = new Queue();
       result.setOpen(true);
       result.setName(name);
       result.setOwner(userOpt.get());
       return new QueueResponse(queueRepository.save(result));

    }

}
