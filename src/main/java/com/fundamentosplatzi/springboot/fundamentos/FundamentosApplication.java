package com.fundamentosplatzi.springboot.fundamentos;

import com.fundamentosplatzi.springboot.fundamentos.bean.MyBean;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyOwnBean;
import com.fundamentosplatzi.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.pojo.UserPojo;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import com.fundamentosplatzi.springboot.fundamentos.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

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
	private UserService userService;

	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean,
								  MyBeanWithDependency myBeanWithDependency, MyOwnBean myOwnBean, MyBeanWithProperties myBeanWithProperties,
								  UserPojo userPojo, UserRepository userRepository, UserService userService){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myOwnBean = myOwnBean;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//previousPractice();
		saveUserInDb();
		getUserInfoJPQL();
		saveWithErrorTransactional();
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

	private void getUserInfoJPQL(){
		LOGGER.info("El usuario encontrado es: " +
				userRepository.findUserByEmail("one@example.com").
				orElseThrow(()-> new RuntimeException("Usuario no encontrado")));

		userRepository.findAndSort("T", Sort.by("name").descending())
				.stream()
				.forEach(user -> LOGGER.info("Usuarios encontrados y ordenados"
				+ user));

		userRepository.findByname("Four")
				.stream()
				.forEach(user -> LOGGER.info("Usuario hallado con Query method: "
				+ user.toString()));

		LOGGER.info("Usuario con querys 2 parametros: "+
				userRepository.findByEmailAndName("five@example.com", "Five").
				orElseThrow(()-> new RuntimeException(("No hay usuario con esos dos"))));

		userRepository.findByNameLike("F%")
				.stream()
				.forEach(user -> LOGGER.info("Usuario por name like Query method: "
				+ user));

		userRepository.findByNameOrEmail("jkasd", "six@example.com")
				.stream()
				.forEach(user -> LOGGER.info("Usuario hallado con OR: "
				+ user));

		userRepository.findByBirthdayBetween(LocalDate.of(2020, 01, 01), LocalDate.of(2021, 12, 30))
				.stream()
				.forEach(user -> LOGGER.info("Usuarios nacidos entre 2020 y 2021: "
				+ user));

		userRepository.findByNameLikeOrderByIdDesc("T%")
				.stream()
				.forEach(user -> LOGGER.info(
						"Usuario query method like y ordenado"
						+ user
				));

		LOGGER.info("Named parameter query user found: "
				+ userRepository.getAllByBirthdayAndEmail(LocalDate.of(2021,07,7), "seven@example.com")
				.orElseThrow(() -> new RuntimeException("No hay usuarios con eso")));
	}

	private void saveWithErrorTransactional(){
		User transactional1 = new User("T1", "t1@example.com", LocalDate.of(2020,01,01));
		User transactional2 = new User("T2", "t2@example.com", LocalDate.of(2020,01,02));
		User transactional3 = new User("T3", "t3@example.com", LocalDate.of(2020,01,03));
		User transactional4 = new User("T4", "t4@example.com", LocalDate.of(2020,01,04));

		List<User> users= Arrays.asList(transactional1, transactional2, transactional3, transactional4);
		userService.saveTransactional(users);
		userService.getAllUsers().stream()
		.forEach(user -> LOGGER.info(
				"Usuario del método transaccional"
				+ user
		));
	}
}
