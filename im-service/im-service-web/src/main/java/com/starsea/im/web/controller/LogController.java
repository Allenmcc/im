package com.starsea.im.web.controller;

import com.starsea.im.aggregation.dto.LogDto;
import com.starsea.im.aggregation.service.LogService;
import com.starsea.im.aggregation.util.ServiceResult;
import com.starsea.im.biz.entity.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/9/15.
 */
@Controller
@RequestMapping("/log")
public class LogController extends AjaxBase{
    @Autowired
    LogService logService;

    @RequestMapping(value = "/getUserLog",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getUserLog(HttpServletRequest req,
                                         @RequestParam("openId") String openId,
                                         @RequestParam("childOpenId") String childOpenId
    ){
        ServiceResult serviceResult = new ServiceResult();
        LogEntity logEntity=new LogEntity();
        logEntity.setOpenId(openId);
        logEntity.setChildOpenId(childOpenId);
        serviceResult.setMsg(logService.queryUserLog(openId, childOpenId));
        serviceResult.setCode(200);
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/addUserLog",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult addUserLog(HttpServletRequest req,
                                    @RequestParam("openId") String openId,
                                    @RequestParam("childOpenId") String childOpenId,
                                    @RequestParam("title") String title,
                                    @RequestParam("content") String content,
                                    @RequestParam("time") String time,
                                    @RequestParam("labels") String labels){
        ServiceResult serviceResult=new ServiceResult();
        LogDto logDto=new LogDto();
        logDto.setOpenId(openId);
        logDto.setChildOpenId(childOpenId);
        logDto.setTitle(title);
        logDto.setLabels(labels);
        logDto.setContent(content);
        logDto.setTime(time);
        serviceResult.setMsg(logService.addUserLog(logDto));
        serviceResult.setCode(200);
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/deleteUserLog",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult deleteUserLog(HttpServletRequest req,
                                    @RequestParam("openId") String openId,
                                    @RequestParam("childOpenId") String childOpenId,
                                    @RequestParam("title") String title,
                                    @RequestParam("content") String content,
                                    @RequestParam("time") String time,
                                    @RequestParam("labels") String labels){
        ServiceResult serviceResult=new ServiceResult();
        LogDto logDto=new LogDto();
        logDto.setOpenId(openId);
        logDto.setChildOpenId(childOpenId);
        logDto.setTitle(title);
        logDto.setLabels(labels);
        logDto.setContent(content);
        logDto.setTime(time);
        serviceResult.setMsg(logService.deleteUserLog(logDto));
        serviceResult.setCode(200);
        return setResponseData(serviceResult);
    }
}
