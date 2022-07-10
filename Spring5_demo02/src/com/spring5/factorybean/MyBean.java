package com.spring5.factorybean;

import com.spring5.collectiontype.Subject;
import org.springframework.beans.factory.FactoryBean;

public class MyBean implements FactoryBean<Subject> {
    @Override
    public Subject getObject() throws Exception {
        return new Subject();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
