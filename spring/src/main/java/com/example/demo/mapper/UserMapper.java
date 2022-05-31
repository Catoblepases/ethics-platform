package com.example.demo.mapper;

import com.example.demo.model.menu.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserMapper {

    @Select("select * from user")
    List<User> findAll();

    @Update("insert into User values (#{userId},#{userName},#{userPassword});")
    @Transactional
    void addUser(User user);

    @Update("Update User set userName=#{userName},userPassword=#{userPassword} where userId=#{userId};")
    @Transactional
    void updateUser(User user);

    @Delete("Delete from User where userId=#{userId}")
    @Transactional
    void deleteUserById(long id);

    @Select("Select * from User Where userId=${userId}")
    @Transactional
    User findUserById(long userId);
}
