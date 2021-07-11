package com.fundamentosplatzi.springboot.fundamentos;

import com.fundamentosplatzi.springboot.fundamentos.bean.MyBean;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyOwnBean;
import com.fundamentosplatzi.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.pojo.UserPojo;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	Log LOGGER = LogFactory.getLog(FundamentosApplication.class);

	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyOwnBean myOwnBean;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;

	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyOwnBean myOwnBean, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myOwnBean = myOwnBean;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//previousPractice();
		saveUserInDb();
	}

	private void previousPractice(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		myOwnBean.multi(2);
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail() + " - " + userPojo.getPassword());
		try{
			int n = 7/0;
		}
		catch(Exception e){
			LOGGER.error("Muestra error al dividir por cero" + e.getMessage());
		}
	}

	private void saveUserInDb(){
		User userOne = new User("One", "one@example.com", LocalDate.of(2021,07,1));
		User userTwo = new User("Two", "two@example.com", LocalDate.of(2021,07,2));
		User userThree = new User("Three", "three@example.com", LocalDate.of(2021,07,3));
		User userFour = new User("Four", "four@example.com", LocalDate.of(2021,07,4));
		User userFive = new User("Five", "five@example.com", LocalDate.of(2021,07,5));
		User userSix = new User("Six", "six@example.com", LocalDate.of(2021,07,6));
		User userSeven = new User("Seven", "seven@example.com", LocalDate.of(2021,07,7));
		User userEight = new User("Eight", "eight@example.com", LocalDate.of(2021,07,8));
		User userNine = new User("Nine", "nine@example.com", LocalDate.of(2021,07,9));
		User userTen = new User("Ten", "ten@example.com", LocalDate.of(2021,07,10));
		List<User> list=Arrays.asList(userOne, userTwo, userThree, userFour, userFive, userSix, userSeven, userEight, userNine, userTen);
		list.stream().forEach(userRepository::save);
	}
}
