package com.spring5.dao;

import com.spring5.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void add(User user) {
        String sql = "insert into t_user values(?,?,?)";
        int update = jdbcTemplate.update(sql, user.getUserId(), user.getUsername(), user.getUserStatus());
        System.out.println(update);
    }

    @Override
    public void update(User user) {
        String sql = "update t_user set username=?,ustatus=? where user_id=?";
        int update = jdbcTemplate.update(sql, user.getUsername(), user.getUserStatus(), user.getUserId());
        System.out.println(update);
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from t_user where user_id=?";
        int delete = jdbcTemplate.update(sql, id);
        System.out.println(delete);
    }

    @Override
    public int selectCount() {
        String sql = "select count(*) from t_user";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public User findOne(Integer id) {
        String sql = "select * from t_user where User_id=?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from t_user";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
    }

    @Override
    public void batchAddUser(List<Object[]> batchArgs) {
        String sql = "insert into t_user values(?,?,?)";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));
    }
}

