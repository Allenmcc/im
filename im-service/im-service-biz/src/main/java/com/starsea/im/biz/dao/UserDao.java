package com.starsea.im.biz.dao;

import com.starsea.im.biz.annotation.DataSource;
import com.starsea.im.biz.annotation.Single;
import com.starsea.im.biz.entity.TeacherRegisterEntity;
import com.starsea.im.biz.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beigua on 2015/8/5.
 */
@Repository
@Single
public interface UserDao {
    @DataSource("red")
    public UserEntity queryUser(int id);

    @DataSource("read")
    public UserEntity queryUserByOpenId(String openId);


    @DataSource("read")
    public List<UserEntity> queryUsers();

    @DataSource("write")
    public int addUser(UserEntity user);

    @DataSource("read")
    public List<UserEntity> queryChildrenUsers(String teacher);

    @DataSource("read")
    public List<UserEntity> queryAllChildren();
    @DataSource("read")
    public List<UserEntity> queryAllTeacher();
    @DataSource("write")
    public int addRegister(TeacherRegisterEntity teacherRegisterEntity);
    @DataSource("read")
    public List<TeacherRegisterEntity> queryTeacherRegister();
    @DataSource("write")
    public int deleteTeacherRegister(String[] registerOpenIds);
    @DataSource("write")
    public int passTeacherRegister(List<TeacherRegisterEntity> teacherRegisterEntities);
    @DataSource("read")
    public TeacherRegisterEntity queryTeacher(@Param("teacherName") String teacerName,@Param("command") String command);
}
