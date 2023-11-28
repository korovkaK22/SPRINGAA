package com.example.springaa.repositories;

import com.example.springaa.entity.Queue;
import com.example.springaa.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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




    //========== Методи, в яких є зв'язок із queue_list ==============

    public List<Integer> getAllUsersIdInQueue(int queue_id) {
        String sql = "SELECT user_id FROM queue_lists WHERE queue_id = ? ORDER BY place_number";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("user_id"), queue_id);
    }


    /**
     * Видаляє юзера з черги, при цьому зміщуючи всі номера наступних людей
     *
     * @param queue_id id черги
     * @param user_id  id юзера
     * @return Чи було видалено користувача з черги
     */
    public boolean deleteUserFromQueue(int queue_id, int user_id) {
        // Знайти місце юзера
        String getPlaceNumberSql = "SELECT place_number FROM queue_lists WHERE queue_id = ? AND user_id = ?";
        Integer place_number = jdbcTemplate.query(
                getPlaceNumberSql,
                rs -> {
                    if (rs.next()) {
                        return rs.getInt("place_number");
                    } else {
                        return null;
                    }
                },
                queue_id, user_id);
        //Якщо юзер справді в цій черзі
        if (place_number != null) {
            // Видалити юзера
            String deleteSql = "DELETE FROM queue_lists WHERE queue_id = ? AND user_id = ?";
            jdbcTemplate.update(deleteSql, queue_id, user_id);

            // оновити номер для інших юзерів
            String updateSql = "UPDATE queue_lists SET place_number = place_number - 1 WHERE queue_id = ? AND place_number > ?";
            jdbcTemplate.update(updateSql, queue_id, place_number);
            return true;
        }
        return false;
    }


    /**
     * Додає юзера на першу вільну комірку в конкретну чергу
     * Не перевіряє чергу на повторення юзера
     * @param queue_id id черги
     * @param user_id  id юзера
     * @return чи додався юзер
     */
    public boolean addUserToQueue(int queue_id, int user_id) {
        // Знайти вільне місце
        String findPlaceSql = "SELECT COALESCE(MAX(place_number), 0) + 1 AS place_number FROM queue_lists " +
                "WHERE queue_id = ? AND NOT EXISTS (SELECT 1 FROM queue_lists ql2 WHERE " +
                "ql2.queue_id = queue_lists.queue_id AND ql2.place_number = queue_lists.place_number + 1)";
        Integer place_number = jdbcTemplate.query(
                findPlaceSql,
                rs -> {rs.next(); return rs.getInt("place_number");},
                queue_id
        );
        //додати туди юзера
        String sql = "INSERT INTO queue_lists (queue_id, user_id, place_number) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, queue_id, user_id, place_number) > 0;
    }


    public Optional<Integer> getUserIdOnPositionInQueue(int queue_id, int place_number) {
        String sql = "SELECT user_id FROM queue_lists WHERE queue_id = ? AND place_number = ? ";
        return jdbcTemplate.query(
                sql,
                rs -> {
                    if (rs.next()) {
                        return Optional.of(rs.getInt("user_id"));
                    } else {
                        return Optional.empty();
                    }
                },
                queue_id, place_number
        );
    }


    /**
     * Перевіряє, чи знаходиться вже юзер у цій черзі
     * @param queue_id id черги
     * @param user_id  id юзера
     * @return чи знаходиться вже юзер у цій черзі
     */
    public boolean isUserInQueue(int queue_id, int user_id) {
        String sql = "SELECT * FROM queue_lists WHERE queue_id = ? AND user_id = ?";
        return Boolean.TRUE.equals(jdbcTemplate.query(
                sql,
                ResultSet::next,
                queue_id, user_id
        ));
    }

}
