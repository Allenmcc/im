package com.starsea.im.web.controller;

import com.starsea.im.aggregation.service.UserService;
import com.starsea.im.aggregation.util.CheckUtil;
import com.starsea.im.aggregation.util.MessageUtil;
import com.starsea.im.aggregation.util.ServiceResult;
import com.starsea.im.biz.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by beigua on 2015/8/5.
 */
@Controller
@RequestMapping("/user")
public class UserController extends AjaxBase{
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getLeadList(HttpServletRequest request) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setMsg(userService.querUsers());
        serviceResult.setCode(200);
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult signUp(HttpServletRequest req,
                                @RequestParam("openId") String openId,
                                @RequestParam("name") String name,
                                @RequestParam("age") Integer age,
                                @RequestParam("sex") String sex,
                                @RequestParam("myClass") String myClass,
                                @RequestParam("school") String school,
                                @RequestParam("organization") String organization,
                                @RequestParam("evaluationPerson") String evaluationPerson
                                ){
        ServiceResult serviceResult = new ServiceResult();
        UserEntity userEntity = new UserEntity();
        userEntity.setOpenId(openId);
        userEntity.setName(name);
        userEntity.setAge(age);
        userEntity.setSex(sex);
        userEntity.setMyClass(myClass);
        userEntity.setSchool(school);
        userEntity.setOrganization(organization);
        userEntity.setEvaluationPerson(evaluationPerson);
        userEntity.setCreateTime(new Date());
        serviceResult.setMsg(userService.addUser(userEntity));
        serviceResult.setCode(200);
        return setResponseData(serviceResult);
    }


    @RequestMapping(value = "/getUserByOpenId",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getUserByOpenId(HttpServletRequest req,
                                @RequestParam("openId") String openId

    ){
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setMsg(userService.queryUserByOpenId(openId));
        serviceResult.setCode(200);
        return setResponseData(serviceResult);
    }

    @RequestMapping(value = "/wx",method = RequestMethod.GET)
    @ResponseBody
    public String verifyWeixin (HttpServletRequest req){
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");

        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return "false";

    }

    @RequestMapping(value = "/wx",method = RequestMethod.POST)
    @ResponseBody
    public String getMessage (HttpServletRequest req){
        try {
            Map<String, String> map = MessageUtil.xmlToMap(req);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            String message = null;

            if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
                if ("1".equals(content)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());

                } else if ("2".equals(content)) {
                    message = MessageUtil.initNewsMessage(toUserName, fromUserName);

                } else if ("?".equals(content)||"？".equals(content)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }

            }else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
                String eventType = map.get("Event");
                if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
                    //message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                    message = MessageUtil.initNewsMessage(toUserName, fromUserName);
                }else if (MessageUtil.MESSAGE_CLICK.equals(eventType)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }else if (MessageUtil.MESSAGE_VIEW.equals(eventType)) {
                    String url = map.get("EventKey");
                    message = MessageUtil.initText(toUserName, fromUserName, url);
                }
            }

            return message;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{

        }
        return "false";


    }



}
