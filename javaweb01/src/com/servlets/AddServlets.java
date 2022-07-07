package com.servlets;

import com.dao.dao.FruitDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlets extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //需要注意的是，设置编码这一行代码必须在所有的获取参数之前
        req.setCharacterEncoding("UTF-8");
        String fname = req.getParameter("fname");
        Integer price = Integer.parseInt(req.getParameter("price"));
        Integer fcount = Integer.parseInt(req.getParameter("fcount"));
        String remark = req.getParameter("remark");

        /*
        System.out.println("fname = " + fname);
        System.out.println("price = " + price);
        System.out.println("fcount = " + fcount);
        System.out.println("remark = " + remark);
         */
        FruitDAO fruitDAO = new FruitDAO();
        int row = fruitDAO.update("insert into fruit values(?,?,?,?)", fname, price, fcount, remark);
        if (row == 1) {
            System.out.println("添加成功");
        }
    }
}
