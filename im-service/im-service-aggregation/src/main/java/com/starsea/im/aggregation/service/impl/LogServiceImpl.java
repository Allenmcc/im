package com.starsea.im.aggregation.service.impl;


import com.starsea.im.aggregation.dto.LogDto;
import com.starsea.im.aggregation.service.LogService;
import com.starsea.im.aggregation.transfor.Transformer;
import com.starsea.im.biz.dao.LogDao;
import com.starsea.im.biz.entity.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/16.
 */
@Service("logService")
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;

    public LogEntity queryUserLog(String openId, String childOpenId) {
            Map<String,String> params=new HashMap<String,String>();
            params.put("openId",openId);
            params.put("childOpenId",childOpenId);
            LogEntity logEntity=logDao.queryLog(params);
            return logEntity;
    }

    public int addUserLog(LogDto logDto) {
        LogEntity logEntity= Transformer.convertLogEntityFromLogDto(logDto);
        //判断数据库是否已有数据  有的话就update  没有就insert
        Map<String,String> params=new HashMap<String,String>();
        params.put("openId",logDto.getOpenId());
        params.put("childOpenId",logDto.getChildOpenId());
        LogEntity logEntity1=logDao.queryLog(params);
        if(logEntity1==null) {
            return logDao.insertLog(logEntity);
        }else{
            return logDao.updateLog(logEntity);
        }
    }

    public int deleteUserLog(LogDto logDto) {
        //先查找该记录的 所有日志、 字符串匹配 删除 匹配项
        Map<String,String> params=new HashMap<String,String>();
        params.put("openId",logDto.getOpenId());
        params.put("childOpenId",logDto.getChildOpenId());
        LogEntity logEntity=logDao.queryLog(params);
        String log=logEntity.getLog();
        LogEntity deleteEntity= Transformer.convertLogEntityFromLogDto(logDto);
        String deleteLog=deleteEntity.getLog();
        int pos=log.indexOf(deleteLog);
        int end=log.indexOf("}",pos);
        log=log.substring(0,pos)+log.substring(end+1,log.length());
        logEntity.setLog(log);
//        System.out.println(log);
        return logDao.deleteLog(logEntity);
    }
}
