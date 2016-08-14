package com.starsea.im.aggregation.service;

import com.starsea.im.aggregation.constant.DataSourceType;
import com.starsea.im.aggregation.dto.SearchDto;
import com.starsea.im.aggregation.dto.StudyFormDto;
import com.starsea.im.aggregation.dto.UserDto;
import com.starsea.im.aggregation.dto.WatchFormDto;
import com.starsea.im.biz.annotation.DataSource;
import com.starsea.im.biz.entity.*;

import java.util.List;

/**
 * Created by beigua on 2015/8/12.
 */
public interface UserService{
    @DataSource(DataSourceType.READ)
    public UserDto isExsit(int id);

    @DataSource(DataSourceType.READ)
    public PageModel queryUsersForPage(SearchDto searchDto);

    @DataSource(DataSourceType.READ)
    public UserDto queryUserByOpenId(String OpenId);

    @DataSource(DataSourceType.READ)
    public List<UserDto> querUsers();

    @DataSource(DataSourceType.WRITE)
    public int insert();

    @DataSource(DataSourceType.WRITE)
    public int update();

    @DataSource(DataSourceType.WRITE)
    public int delete();

    @DataSource(DataSourceType.WRITE)
    public int addUser(UserEntity userEntity);

    }