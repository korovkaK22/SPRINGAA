package com.example.springaa.repositories;

import com.example.springaa.entity.Queue;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Transactional
public class JDBCQueueRepository {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Queue> queueMapper = new BeanPropertyRowMapper<>(Queue.class);
    ;

    @Autowired
    public JDBCQueueRepository(JdbcTemplate jdbcTemplate) throws SQLException {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int create(Queue queue) throws SQLException {
        String sql = "INSERT INTO queues (name, is_open, owner_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, queue.getName());
            ps.setBoolean(2, queue.getIsOpen());
            ps.setInt(3, queue.getOwner().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
    }


    public Optional<Queue> findById(int id) throws SQLException {
        String sql = "SELECT * FROM queues WHERE id = ?";
        return jdbcTemplate.query(sql, queueMapper, id)
                .stream().findAny();
    }

    public List<Queue> findLastQueues(int amount) {
        String sql = "SELECT * FROM queues ORDER BY id DESC LIMIT ?";
        return jdbcTemplate.query(sql, queueMapper, amount);
    }

    public boolean update(Queue queue) {
        String sql = "UPDATE queues SET name = ?, is_open = ?, owner_id = ? WHERE id = ?";
        return jdbcTemplate.update(sql, queue.getName(), queue.getIsOpen(), queue.getOwner().getId(), queue.getId()) > 0;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM queues WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
