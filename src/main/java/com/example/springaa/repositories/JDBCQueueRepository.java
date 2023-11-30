package com.example.springaa.repositories;

import com.example.springaa.entity.Queue;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface JDBCQueueRepository {

    int create(Queue queue) throws SQLException;

    Optional<Queue> findById(int id) throws SQLException;

    List<Integer> findLastQueueIds(int amount);

    boolean update(int id, String name, boolean isOpen, int ownerId);

    boolean delete(int id);

    List<Integer> getAllUsersIdInQueue(int queue_id);

    boolean deleteUserFromQueue(int queue_id, int user_id);

    boolean addUserToQueue(int queue_id, int user_id);

    Optional<Integer> getUserIdOnPositionInQueue(int queue_id, int place_number);

    boolean isUserInQueue(int queue_id, int user_id);
}
