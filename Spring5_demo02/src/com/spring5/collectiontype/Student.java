package com.spring5.collectiontype;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Student {
    //1 数组类型属性
    private String[] subjectNames;

    //2 list集合类型属性
    private List<String> list;
    private List<Subject> subjects;

    //3 map集合类型属性
    private Map<String, String> maps;

    //4 set集合类型属性
    private Set<String> sets;

    public void setSets(Set<String> sets) {
        this.sets = sets;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    public void setSubjectNames(String[] subjectNames) {
        this.subjectNames = subjectNames;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
