package com.fundamentosplatzi.springboot.fundamentos.bean;

public class MyBeanWithPropertiesImplement implements MyBeanWithProperties{

    private String name;
    private String surname;

    public MyBeanWithPropertiesImplement(String nombre, String apellido) {
        this.name = nombre;
        this.surname = apellido;
    }

    @Override
    public String function() {
        return name + " - " + surname;
    }
}
