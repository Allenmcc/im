package com.starsea.im.biz.dao.e;

import com.starsea.im.biz.entity.e.MessageList;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/31.
 */
@Repository
public interface JingXuanDao {

    public int getRowCount(String tableName);
    public List<MessageList> selectByParams(Map<String, Object> params);
}
