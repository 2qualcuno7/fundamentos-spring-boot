package com.fundamentosplatzi.springboot.fundamentos.bean;

public class MyBeanTwoImplement implements MyBean{
    @Override
    public void print() {
        System.out.println("Aquí llamando desde el segundo bean configurado como yo quiero");
    }
}
