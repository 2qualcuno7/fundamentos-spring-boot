package com.fundamentosplatzi.springboot.fundamentos.bean;

public class MyBeanWithDepedencyImplement implements MyBeanWithDependency{
    private MyOperation myOperation;

    public MyBeanWithDepedencyImplement(MyOperation myOperation) {
        this.myOperation = myOperation;
    }

    @Override
    public void printWithDependency() {
        int num = 7;
        int resul = this.myOperation.sum(num);
        System.out.println(resul);
    }
}
