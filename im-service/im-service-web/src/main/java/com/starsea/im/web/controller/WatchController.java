package com.starsea.im.web.controller;

import com.starsea.im.aggregation.service.decodeService;
import com.starsea.im.aggregation.service.impl.DecodeService;
import com.starsea.im.aggregation.service.impl.WatchService;
import com.starsea.im.aggregation.transfor.Transformer;
import com.starsea.im.aggregation.util.ServiceResult;
import com.starsea.im.biz.entity.WatchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * Created by danny on 16/4/28.
 */
@Controller
@RequestMapping("/watch")
public class WatchController extends AjaxBase {

    @Autowired
    WatchService watchService;
    @Autowired
    DecodeService utilService;

    @RequestMapping(value = "/addWatch", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult addWatch(HttpServletRequest req,
                                  @RequestParam(value = "openId") String openId,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "teacher") String teacher,
                                  @RequestParam(value = "evaluationTime") String evaluationTime,
                                  @RequestParam(value = "now_score[]") int[] now_score,
                                  @RequestParam(value = "now_comment[]") String[] now_comment
    ) throws ParseException {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        WatchForm watchForm = Transformer.enrichWatchForm(name, teacher, evaluationTime,openId, now_score, now_comment);
        serviceResult.setMsg(watchService.addWatchForm(watchForm));
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/getLastWatchForm", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getLastWatchForm(@RequestParam(value = "name",defaultValue = "黑仔一号") String name) {
        name=utilService.decode(name);
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(watchService.queryLastWatchFormByName(name));
        return setResponseData(serviceResult);
    }


    @RequestMapping(value = "/getWatchFormByOpenId", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getWatchFormByOpenId(@RequestParam(value = "openId") String openId) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(watchService.queryWatchFormByOpenId(openId));
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/getLastWatchFormByNameWeek", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getLastWatchFormByNameWeek(@RequestParam(value = "name",defaultValue = "黑仔一号") String name) {
        name=utilService.decode(name);
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(watchService.queryLastWatchFormByNameWeek(name));
        return setResponseData(serviceResult);
    }


    @RequestMapping(value = "/queryLastWatchFormByNameMonth", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult queryLastWatchFormByNameMonth(@RequestParam(value = "name",defaultValue = "黑仔一号") String name) {
        name=utilService.decode(name);
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(watchService.queryLastWatchFormByNameMonth(name));
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/queryAvgWatchFormByNameDay", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult queryAvgWatchFormByNameDay(@RequestParam(value = "name",defaultValue = "黑仔一号") String name,
                                                    @RequestParam(value = "day",defaultValue = "7") int day
    ) {
        name=utilService.decode(name);
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(watchService.queryAvgWatchFormByNameDay(name, day));
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/queryLastWatchFormByOpenIdWeek", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult queryLastWatchFormByOpenIdWeek(@RequestParam(value = "openId") String openId) {
//        openId=utilService.decode(openId);
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(watchService.queryLastWatchFormByOpenIdWeek(openId));
        return setResponseData(serviceResult);
    }



    @RequestMapping(value = "/queryLastWatchFormByOpenIdMonth", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult queryLastWatchFormByOpenIdMonth(@RequestParam(value = "openId") String openId) {
//        name=utilService.decode(name);
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(watchService.queryLastWatchFormByOpenIdMonth(openId));
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/queryAvgWatchFormByOpenIdDay", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult queryAvgWatchFormByOpenIdDay(@RequestParam(value = "openId") String name,
                                                    @RequestParam(value = "day",defaultValue = "7") int day
    ) {
        name=utilService.decode(name);
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(watchService.queryAvgWatchFormByOpenIdDay(name, day));
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/queryOneTeacherAllChildrenByOpenIdWeek", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult queryOneTeacherAllChildrenByOpenIdWeek(@RequestParam(value = "openId") String openId) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(watchService.queryOneTeacherAllChildrenByOpenIdWeek(openId));
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/queryOneTeacherAllChildrenByOpenIdMonth", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult queryOneTeacherAllChildrenByOpenIdMonth(@RequestParam(value = "openId") String openId) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(watchService.queryOneTeacherAllChildrenByOpenIdMonth(openId));
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/queryAllChildrenByOpenIdWeek", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult queryAllChildrenByOpenIdWeek() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(watchService.queryAllChildrenByOpenIdWeek());
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/queryAllChildrenByOpenIdMonth", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult queryAllChildrenByOpenIdMonth() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(watchService.queryAllChildrenByOpenIdMonth());
        return setResponseData(serviceResult);
    }
}