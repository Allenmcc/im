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
        return userDao.addUser(userEntity);
    }

}