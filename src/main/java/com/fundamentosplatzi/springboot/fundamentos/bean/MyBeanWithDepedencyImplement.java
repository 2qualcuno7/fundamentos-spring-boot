package com.fundamentosplatzi.springboot.fundamentos.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyBeanWithDepedencyImplement implements MyBeanWithDependency{
    Log LOGGER = LogFactory.getLog(MyBeanWithDepedencyImplement.class);

    private MyOperation myOperation;

    public MyBeanWithDepedencyImplement(MyOperation myOperation) {
        this.myOperation = myOperation;
    }

    @Override
    public void printWithDependency() {
        LOGGER.info("Se ingresó a printWithDependency");
        int num = 7;
        LOGGER.debug("El numero enviado a la depende es " + num);
        int resul = this.myOperation.sum(num);
        System.out.println(resul);
    }
}
