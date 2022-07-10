package com.spring5.dao;

import com.spring5.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface BookDAO {
    void add(User user);
    void update(User user);
    void delete(Integer id);
    int selectCount();

    User findOne(Integer id);

    List<User> findAll();

    void batchAddUser(List<Object[]> batchArgs);
}
