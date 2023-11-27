package com.example.springaa.repositories;

import com.example.springaa.entity.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JDBCQueueRepository {

    private final DataSource dataSource;

    @Autowired
    public JDBCQueueRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Queue create(Queue queue) throws SQLException {
        String sql = "INSERT INTO queues (name, is_open, owner_id) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, queue.getName());
            statement.setBoolean(2, queue.isOpen());
            statement.setInt(3, queue.getOwner().getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating queue failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    queue.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating queue failed, no ID obtained.");
                }
            }
        }
        return queue;
    }

    public Queue findById(int id) throws SQLException {
        String sql = "SELECT * FROM queues WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Queue(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getBoolean("is_open"));
            }
        }
        return null;
    }
//
//    public List<Queue> findAll() throws SQLException {
//        List<Queue> queues = new ArrayList<>();
//        String sql = "SELECT * FROM queues";
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            while (resultSet.next()) {
//                queues.add(new Queue(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getBoolean("is_open"), ...));
//            }
//        }
//        return queues;
//    }

    public boolean update(Queue queue) throws SQLException {
        String sql = "UPDATE queues SET name = ?, is_open = ?, owner_id = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, queue.getName());
            statement.setBoolean(2, queue.isOpen());
            statement.setInt(3, queue.getOwner().getId());
            statement.setInt(4, queue.getId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM queues WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }
}
