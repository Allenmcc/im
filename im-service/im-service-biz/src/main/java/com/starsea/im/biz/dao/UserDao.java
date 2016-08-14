package com.starsea.im.biz.dao;

import com.starsea.im.biz.annotation.DataSource;
import com.starsea.im.biz.annotation.Single;
import com.starsea.im.biz.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beigua on 2015/8/5.
 */
@Repository
@Single
public interface UserDao {
    @DataSource("write")
    public UserEntity queryUser(int id);

    @DataSource("read")
    public UserEntity queryUserByOpenId(String openId);


    @DataSource("read")
    public List<UserEntity> queryUsers();

    //使用@Insert注解指明add方法要执行的SQL
//    @Insert("insert into users(email, password) values(#{email}, #{password})")
    @DataSource("write")
    public int addUser(UserEntity user);

}
