package com.spring5.service;

import com.spring5.dao.BookDAO;
import com.spring5.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookDAO bookDAO;

    //添加用户
    public void addUser(User user) {
        bookDAO.add(user);
    }

    //修改用户
    public void updateUser(User user) {
        bookDAO.update(user);
    }

    //删除用户
    public void deleteUser(Integer id) {
        bookDAO.delete(id);
    }

    //查询表中的记录数
    public int findCount() {
        int i = bookDAO.selectCount();
        return i;
    }

    //查询返回对象
    public User findOne(int id) {
        return bookDAO.findOne(id);
    }

    //查询返回集合
    public List<User> findAll() {
        return bookDAO.findAll();
    }

    //批量添加
    public void batchAddUser(List<Object[]> batchArgs) {
        bookDAO.batchAddUser(batchArgs);
    }
}
