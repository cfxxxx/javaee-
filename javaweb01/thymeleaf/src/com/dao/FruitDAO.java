package com.dao;

import com.bean.Fruit;

import java.util.List;

public class FruitDAO extends BasicDAO<Fruit>{
    public List<com.bean.Fruit> getFruitList() {
        return super.queryMulti("select * from fruit", com.bean.Fruit.class);
    }
}
