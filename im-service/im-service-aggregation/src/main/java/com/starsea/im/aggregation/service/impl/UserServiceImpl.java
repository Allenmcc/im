package com.starsea.im.aggregation.service.impl;

import com.google.common.collect.Lists;
import com.starsea.im.aggregation.aop.LogParams;
import com.starsea.im.aggregation.dto.SearchDto;
import com.starsea.im.aggregation.dto.StudyFormDto;
import com.starsea.im.aggregation.dto.UserDto;
import com.starsea.im.aggregation.dto.WatchFormDto;
import com.starsea.im.aggregation.transfor.Transformer;
import com.starsea.im.biz.entity.*;
import com.starsea.im.aggregation.service.UserService;
import com.starsea.im.aggregation.transfor.UserTransfor;
import com.starsea.im.biz.dao.UserDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beigua on 2015/8/5.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    @LogParams
    public UserDto isExsit(int id){
        UserEntity entity = userDao.queryUser(id);
        UserDto userDto = new UserDto();
        if(entity != null){ BeanUtils.copyProperties(entity, userDto);}
        return userDto;
    }

    @Override
    @LogParams
    public PageModel queryUsersForPage(SearchDto searchDto) {
        return null;
    }

    @LogParams
    public UserDto queryUserByOpenId(String OpenId) {

        UserEntity userEntity = userDao.queryUserByOpenId(OpenId);
        UserDto userDto = new UserDto();
        if(userEntity != null){ BeanUtils.copyProperties(userEntity, userDto);}
        return userDto;
    }

    @Override
    @LogParams
    public List<UserDto> querUsers() {
        return Lists.transform(userDao.queryUsers(), UserTransfor.INSTANCE);
    }

    @Override
    public int insert() {

        return 0;
    }

    @Override
    public int update() {
        return 0;
    }

    @Override
    public int delete() {
        return 0;
    }


    @Override
    public int addUser(UserEntity userEntity){
        String teacerName=userEntity.getTeacher();
        String command=userEntity.getCommand();
        TeacherRegisterEntity teacherRegisterEntity=userDao.queryTeacher(teacerName,command);
        if(teacherRegisterEntity!=null){
            return userDao.addUser(userEntity);
        }
        return -1;
    }

    @Override
    @LogParams
    public List<UserEntity> queryChildrenUserByOpenId(String OpenId) {
        UserEntity userEntity = userDao.queryUserByOpenId(OpenId);
        String name=userEntity.getName();

        List<UserEntity> userEntities=userDao.queryChildrenUsers(name);
        return userEntities;
//        return Lists.transform(userDao.queryChildrenUsers(), UserTransfor.INSTANCE);
    }

    @Override
    public int addRegister(TeacherRegisterEntity teacherRegisterEntity) {
        return userDao.addRegister(teacherRegisterEntity);
    }

    @Override
    public List<TeacherRegisterEntity> queryTeacherRegister() {
        return userDao.queryTeacherRegister();
    }

    @Override
    public int deleteTeacherRegister(String[] registerOpenIds) {
        return userDao.deleteTeacherRegister(registerOpenIds);
    }

    @Override
    public int passTeacherRegister(String[] yesTeachers) {
        List<TeacherRegisterEntity> teacherRegisterEntities=new ArrayList<TeacherRegisterEntity>();
        for(int i=0;i<yesTeachers.length;i+=5){
            TeacherRegisterEntity teacherRegisterEntity=new TeacherRegisterEntity();
            teacherRegisterEntity.setOpenId(yesTeachers[i]);
            teacherRegisterEntity.setTeacherName(yesTeachers[i + 1]);
            teacherRegisterEntity.setTeacherSchool(yesTeachers[i + 2]);
            teacherRegisterEntity.setTeacherCommand(yesTeachers[i + 3]);
            teacherRegisterEntity.setCreateTime(yesTeachers[i + 4]);
            teacherRegisterEntities.add(teacherRegisterEntity);
        }
        return userDao.passTeacherRegister(teacherRegisterEntities);
    }
}