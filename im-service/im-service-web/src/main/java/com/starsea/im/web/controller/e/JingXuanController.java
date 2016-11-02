package com.starsea.im.web.controller.e;

import com.starsea.im.aggregation.service.e.JingXuanService;
import com.starsea.im.biz.entity.e.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/10/31.
 */
@Controller
@RequestMapping("/e")
public class JingXuanController {
    @Autowired
    private JingXuanService jingXuanService;

    @RequestMapping("ren")
    public String ren(HttpServletRequest request){
        PageParam pageParam=jingXuanService.getPage(request.getParameter("page"),"test.e_ren");
        request.setAttribute("pageParam",pageParam);
        return "e/jingxuan_ren";
    }
}

