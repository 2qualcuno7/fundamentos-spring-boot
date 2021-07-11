package com.fundamentosplatzi.springboot.fundamentos.repository;

import com.fundamentosplatzi.springboot.fundamentos.dtos.UserDto;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @Query("Select u from User u WHERE u.email=?1")
    Optional<User> findUserByEmail(String email);

    @Query("Select u from User u WHERE u.name like ?1%")
    List<User> findAndSort(String name, Sort sort);

    List<User> findByname(String name);
    Optional<User> findByEmailAndName(String email, String name);

    List<User> findByNameLike(String partName);
    List<User> findByNameOrEmail(String name, String email);
    List<User> findByBirthdayBetween(LocalDate begin, LocalDate end);
    List<User> findByNameLikeOrderByIdDesc(String name);

    @Query("Select new com.fundamentosplatzi.springboot.fundamentos.dtos.UserDto(u.id, u.name, u.birthday)" +
            "from User u " +
            "where u.birthday=:parameterDate " +
            "and u.email=:parameterEmail")
    Optional<UserDto> getAllByBirthdayAndEmail(@Param("parameterDate") LocalDate date,
                                               @Param("parameterEmail") String email);
}
