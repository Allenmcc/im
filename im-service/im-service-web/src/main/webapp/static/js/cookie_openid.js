/**
 * Created by Administrator on 2016/8/12.
 */
//用户获取cookie中的微信ID
function getCookie(cookie_name){
    //return "o45t9wZx7eQo5VIB4nTY_76TCW4w";
    var allCookie=document.cookie;
    var cookie_pos=allCookie.indexOf(cookie_name);
    if(cookie_pos!=-1){
        cookie_pos+=cookie_name.length+1;
        var cookie_end=allCookie.indexOf(";",cookie_pos);
        if(cookie_end==-1){
            cookie_end=allCookie.length;
        }
        var value=allCookie.substring(cookie_pos,cookie_end);
        //alert(value);
        return value;
    }
    return false;
}
function setCookie(name,val,time){//time 以 天 为单位
    var theCookie = name + "=" + val;
    var date = new Date();
    date.setTime(date.getTime()+1000*60*60*24*time);
    var cookieDate = date.toGMTString();
    theCookie += ";expires=" + cookieDate+";path=/";
    document.cookie = theCookie;
}

//判断用户有没有openid和数据库中有没有记录
function judgeCookie(child) {
    //家长版 要获得openid   老师版 要获得 孩子的信息childOpenid
    //alert(getCookie("openid"));
    var flag = getCookie("role");//flag标志角色  p家长 t老师
    var cookie_id;
    if(flag=='p') {
        cookie_id = getCookie("openid");
        //alert(cookie_id);
    }else if(flag=='t'){
        cookie_id=getCookie("childOpenid");
        //alert(cookie_id);
        $(".button_submit").css('display','none');
        $(".button_addcom").css('display','none');
    }
    //alert(cookie_id);
    //if (!cookie_id) {  //没有cookie，第一次访问，跳转值注册页面
    //    //这个URL 是向open.weixin.qq.com发送授权请求，映射到后端的接口，获得openid，并设置到cookie中，响应
    //     //处理授权的操作
    //     window.location.href ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9b08b42b34258af7&redirect_uri=http%3A%2F%2Fwww.elastictime.cn%2Fstarsea%2Fopenid&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
    //
    //} else {//有cookie的话
        child.openid=cookie_id;
        $.ajax({//查询数据库请求 看有没有用户有没有填写注册信息
            type: "GET",
            url: "../starsea/user/getUserByOpenId",  //查询数据库接口
            dataType: "json",
            data: {
                openId: cookie_id
            },
            async:false,
            success: function (data) {
                //如果有记录，ServiceResult的code为200，反之为500 返回的json为{code,{code,msg}}
                //if (data['msg']['msg']['name']==null) { //没有记录，跳转至注册界面
                //    window.location.href = '../../userMessage.html';
                //} else {//有记录的话  传回孩子姓名，父母姓名，opednid等信息供 获取历史信息（通过openid查询） 提交（孩子姓名，父母姓名） 进行后面的操作
                    child.name= data['msg']['msg']['name'];
                    child.school=data['msg']['msg']['school'];
                    child.myClass=data['msg']['msg']['myClass'];
                    child.organization=data['msg']['msg']['organization'];
                    child.teacher=data['msg']['msg']['teacher'];
                }
            //}
        });

    //}
}