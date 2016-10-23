package com.starsea.im.web.controller;

import com.starsea.im.aggregation.service.DiagnoseService;
import com.starsea.im.aggregation.service.decodeService;
import com.starsea.im.aggregation.transfor.Transformer;
import com.starsea.im.aggregation.util.ServiceResult;
import com.starsea.im.biz.entity.StudyForm;
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
@RequestMapping("/diagnose")
public class DiagnoseController extends AjaxBase{

    @Autowired
    DiagnoseService diagnoseService;
    @Autowired
    decodeService utilService;

    @RequestMapping(value = "/addStudyForm", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult addStudyForm(HttpServletRequest req,
                                         @RequestParam(value = "openId") String openId,
                                         @RequestParam(value = "childOpenId") String childOpenId,
                                         @RequestParam(value = "myName") String name,
                                         @RequestParam(value = "teacher") String teacher,
                                         @RequestParam(value = "evaluationTime") String evaluationTime,
                                         @RequestParam(value = "hc[]") int[] hc) throws ParseException {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg("ok");

        StudyForm studyForm = Transformer.enrichStudyForm(name,teacher, evaluationTime,openId ,hc,childOpenId);
        serviceResult.setMsg(diagnoseService.addStudyForm(studyForm));
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/getStudyForm", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getStudyForm() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(diagnoseService.queryStudyForm());
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/getLastStudyForm", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getLastStudyForm(@RequestParam(value = "name",defaultValue = "黑仔二号") String name) {
        name=utilService.decode(name);
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(diagnoseService.queryLastStudyFormByName(name));
        return setResponseData(serviceResult);
    }


    @RequestMapping(value = "/getStudyFormByOpenId", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getStudyFormByOpenId(@RequestParam(value = "openId") String openId,
                                              @RequestParam(value = "childOpenId") String childOpenId) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(diagnoseService.queryStudyFormByOpenId(openId, childOpenId));
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/getAvg", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getAvg() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(diagnoseService.getAvgWithStudents());
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/getStd", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getStd() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(diagnoseService.getStdWithStudents());
        return setResponseData(serviceResult);
    }



    @RequestMapping(value = "/getStdScore", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getStdScore() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(diagnoseService.getStdScore());
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/getRegularScore", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getRegularScore() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(diagnoseService.getRegularScore());
        return setResponseData(serviceResult);
    }


    @RequestMapping(value = "/getFinalRegularScore", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getFinalRegularScore() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(diagnoseService.getFinalRegularScore());
        return setResponseData(serviceResult);
    }


    @RequestMapping(value = "/getFinalStdScore", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getFinalStdScore() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(diagnoseService.getFinalStdScore());
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/getFinalCommentByOpenId", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getFinalCommentByOpenId(@RequestParam(value = "openId") String openId,
                                                 @RequestParam(value = "childOpenId") String childOpenId) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(diagnoseService.getFinalCommentByOpenId(openId));
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/queryOneTeacherAllChildren", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult queryOneTeacherAllChildren(@RequestParam(value = "openId") String openId) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(diagnoseService.queryOneTeacherAllChildren(openId));
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/queryAllChildren", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult queryAllChildren() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(200);
        serviceResult.setMsg(diagnoseService.queryAllChildren());
        return setResponseData(serviceResult);
    }

}
