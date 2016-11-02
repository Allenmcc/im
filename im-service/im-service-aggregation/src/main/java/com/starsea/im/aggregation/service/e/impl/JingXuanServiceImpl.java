package com.starsea.im.aggregation.service.e.impl;

import com.starsea.im.aggregation.service.e.JingXuanService;
import com.starsea.im.biz.dao.e.JingXuanDao;
import com.starsea.im.biz.entity.e.MessageList;
import com.starsea.im.biz.entity.e.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/31.
 */
@Service("jingXuanService")
public class JingXuanServiceImpl implements JingXuanService{

    @Autowired
    JingXuanDao jingXuanDao;

    public PageParam getPage(String page,String tableName) {
        String currPageStr=page;
        int currPage=1;
        try {
            currPage=Integer.parseInt(currPageStr);
        }catch (Exception e){}

        int rowCount=getRowCount(tableName);
        PageParam pageParam=new PageParam();
        pageParam.setRowCount(rowCount);
        if(pageParam.getTotalPage()<currPage){
            currPage=pageParam.getTotalPage();
        }
        pageParam.setCurrentPage(currPage);
        pageParam=getMessageListByPage(pageParam,tableName);

        return pageParam;
    }

    public int getRowCount(String tableName) {
        return jingXuanDao.getRowCount(tableName);
    }

    //获得一个页面的内容
    public PageParam getMessageListByPage(PageParam pageParam,String tableName) {
        //limit:offset,size
        int currPage=pageParam.getCurrentPage();
        int offset=(currPage-1)*PageParam.pageSize;  //当前页面第一个记录的位置
        int size=PageParam.pageSize;//一个页面最大10个记录
        Map<String ,Object> params=new HashMap<String ,Object>();
        params.put("offset",offset);
        params.put("size",size);
        params.put("tableName",tableName);
        List<MessageList> ipList=jingXuanDao.selectByParams(params);//传过去的是 该页面的第一个记录在数据库中的位置(offset)，从该位置数最多10个
        pageParam.setData(ipList);
        return pageParam;
    }
}
