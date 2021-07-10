package com.fundamentosplatzi.springboot.fundamentos.bean;

public class MyOwnBeanImplement implements MyOwnBean{
    private MyOperation myOperation;

    public MyOwnBeanImplement(MyOperation myOperation) {
        this.myOperation = myOperation;
    }

    @Override
    public void multi(int num) {
        int resul = 0;
        for(int i=0; i<num; i++){
            resul += this.myOperation.sum(num);
        }
        System.out.println(resul);
    }
}
