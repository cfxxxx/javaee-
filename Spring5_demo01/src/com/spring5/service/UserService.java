package com.spring5.service;

import com.spring5.dao.UserDAO;

public class UserService {
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void add() {
        System.out.println("service add...");
        userDAO.update();
    }
}
