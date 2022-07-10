package com.spring5.service;

import com.spring5.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false, timeout = -1, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public void money() {
//        try {
            //第一步 开启事务


            //第二步 进行业务操作
            userDAO.reduceMoney();
            userDAO.addMoney();

            //第三步 没有发生异常，提交事务
//        } catch (Exception e) {
//            //第四步 出现异常，事务回滚
//            throw new RuntimeException(e);
//        }
    }
}
