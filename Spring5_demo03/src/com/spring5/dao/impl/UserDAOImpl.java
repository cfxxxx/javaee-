package com.spring5.dao.impl;

import com.spring5.dao.UserDAO;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class UserDAOImpl implements UserDAO {

    @Override
    public void add() {
        System.out.println("dao add...");
    }
}
