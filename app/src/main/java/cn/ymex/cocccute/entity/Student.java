package cn.ymex.cocccute.entity;

import java.util.List;

/**
 * Copyright (c) 2015. ymex
 * Email:ymex@foxmail.com  (www.ymex.cn)
 * @author ymex
 * Description:: TODO
 * date: 2015/12/3 0003
 **/
public class Student {
    private String name ;
    private int age;
    private String  color ;


    public Student(String name, int age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
