/**
 * Created by Administrator on 2016/9/1.
 */
//获取openid
//function getOpenid(){
//    var openid = getCookie("openid");
//    if (openid==false) {
//        var appid='wx04847323bd5a6669';
//        window.location.href ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx04847323bd5a6669&redirect_uri=http%3A%2F%2Fwww.elastictime.cn%2Fstarsea%2Fopenid&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
//    }
//    return openid;
//}

$(document).ready(function () {
    //setCookie("role","t",1000);
    var color=getCookie("bgColor");
        $("body").css("backgroundColor",color);
    //删除cookie的方法， 第三个参数设置为负的
    //setCookie("openid","o45t9wZx7eQo5VIB4nTY_76TCW4w",3000);
    //setCookie("role","1",-1);
    var openid = getCookie("openid");
if (openid==false) {
    var appid='wx04847323bd5a6669';
    window.location.href ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx04847323bd5a6669&redirect_uri=http%3A%2F%2Fwww.elastictime.cn%2Fstarsea%2Fopenid&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
}else {
    var role = getCookie("role");//p为家长 t为老师
    //alert("1." + role);
    //alert(role);
    //setCookie("role","t",3000);
    if (!role) {//第一次
        //alert("第一次");
        //获取角色信息
        $.ajax({
            type: "GET",
            url: "../starsea/user/getUserByOpenId",
            dataType: "json",
            data: {
                openId: openid
            },
            async: false,
            success: function (data) {
                //alert(1);
                var f = data['msg']['msg']['role'];
                //var f="t";
                if (f == "老师") {
                    //alert("return老师");
                    //将角色设置到cookie
                    setCookie("role", "t", 10000);
                    //window.location.reload();
                    window.location.href="../../common_fourPoint.html";
                } else if (f == "家长") {
                    //alert("return家长");
                    setCookie("role", "p", 10000);
                    //window.location.reload();
                     window.location.href="../../common_fourPoint.html";
                } else {
                    //alert("common:"+openid);
                    //alert("没有权限");
                    //WeixinJSBridge.invoke('closeWindow',{},function(res){});
                    window.location.href = '../../userMessage.html';
                }
            }
        });
    }
    //alert(role);
    //var r=getCookie("role");
    if (role == "p") {//家长
        //window.location.reload();
        //alert("家长：" + role);
        //$("body").addClass("parentBackground");
        $(".t_pic img").attr("src", "static/image/parent.jpg");
        $('.fourPoint').css('display', 'block');
        $('.children').css('display', 'none');

        $('.p1').click(function () {
            window.location.href = "../../positiveTest.html";
        });
        $('.p2').click(function () {
            window.location.href = "../../watchForm.html";
        });
        $('.p3').click(function () {
            window.location.href = "../../picture.html";
        });
        $('.p4').click(function () {
            window.location.href = "../../newFind.html";
        });
    }
    //老师
    else if (role == 't') {
        //window.location.reload();
        //alert("老师：" + role);
        //$("body").addClass("teacherBackground");
        $(".t_pic img").attr("src", "static/image/teacher.jpg");
        var children = [];
        //$('.fourPoint').css('display', 'none');
        //$('.children').css('display', 'block');
        //$('.head').css('display', 'none');
        if (document.referrer.substring(document.referrer.length - 17) == 'positiveTest.html' || document.referrer.substring(document.referrer.length - 12) == 'picture.html' || document.referrer.substring(document.referrer.length - 12) == 'newFind.html' || document.referrer.substring(document.referrer.length - 14) == 'watchForm.html'|| document.referrer.substring(document.referrer.length - 8) == 'set.html') {
            //alert(document.referrer.substring(document.referrer.length-17));
            //alert(document.referrer.substring(document.referrer.length-12));
            $('.fourPoint').css('display', 'block');
            $('.children').css('display', 'none');
            $('.head').css('display', 'block');
        } else {
            $('.fourPoint').css('display', 'none');
            $('.children').css('display', 'block');
            $('.head').css('display', 'none');
        }
        //children=[{openid:"o45t9wZx7eQo5VIB4nTY_76TCW4w",name:"小明",school:"华师",class:"一年级"},
        //    {openid:"cdscvdsvsdd",name:"小红",school:"华师aaaaaaa",class:"二年级aaaaa"},
        //    {openid:"cevrvrvr",name:"小白",school:"华师",class:"三年级"},
        //    {openid:"cdscvvfdsdd",name:"小蓝",school:"华师",class:"四年级"},
        //    {openid:"cdsacsdsdd",name:"小绿",school:"华师",class:"五年级"},
        //    {openid:"vregthvsdd",name:"小黑",school:"华师",class:"六年级"},
        //    {openid:"pioiysvsdd",name:"小紫",school:"华师",class:"七年级"}];

        //获取老师管理的孩子openid
        $.ajax({
            type: "GET",
            url: "../starsea/user/getChildrenUserByOpenId",
            dataType: "json",
            data: {
                openId: openid
            },
            async: false,
            success: function (data) {
                var content = data['msg']['msg'];//获取孩子信息
                for (var i = 0; i < content.length; i++) {
                    children[i] = {openid: "", name: "", school: "", class: ""};
                    children[i].openid = content[i].openId;
                    children[i].name = content[i].name;
                    children[i].school = content[i].school;
                    children[i].class = content[i].myClass;
                }
            }
        });

        var num = children.length;
        var div = document.querySelector('.children');
        var button = document.createElement('button');
        button.className = 'self';
        button.addEventListener("click", function () {
            window.location.href = '../../picture_self.html';//显示所有孩子 所有情况的总的
        });
        //div.appendChild(button);
        var div_11 = document.createElement('div');
        div_11.className="div_11";

        for (var i = 0; i < num; i++) {
            var div_child = document.createElement('div');
            div_child.className = "divChild div" + i;
            var button_child = document.createElement('button');
            button_child.innerHTML = children[i].name;
            //button_child.name=i;
            button_child.className = "child";
            button_child.value = children[i].openid;
            var text_child = document.createElement("label");
            text_child.className = "text";
            text_child.innerHTML = "学校：" + children[i].school + "<br/>年级：" + children[i].class;
            //text_child.style = "display:none";
            //var ok_child = document.createElement('button');
            //ok_child.innerHTML = "确定";
            //ok_child.className = "ok";
            //ok_child.style = "display:none";
            //
            //ok_child.addEventListener("click", function () {
            //    //alert(1);
            //    setCookie('childOpenid', $(this).prev().prev().val(), 1);
            //    $('.fourPoint').css('display', 'block');
            //    $('.children').css('display', 'none');
            //    $('.head').css('display', 'block');
            //    $('.p1').click(function () {
            //        window.location.href = "../../positiveTest.html";
            //    });
            //    $('.p2').click(function () {
            //        window.location.href = "../../watchForm.html";
            //    });
            //    $('.p3').click(function () {
            //        window.location.href = "../../picture.html";
            //    });
            //    $('.p4').click(function () {
            //        window.location.href = "../../newFind.html";
            //    });
            //});

            button_child.addEventListener("click", function () {
                $('.child').removeClass('select');
                //$('.text').css('display', 'none');
                //$('.text').next().css('display', 'none');
                //$(this).next().css('display', 'block');
                //$(this).next().next().css('display', 'block');
                $(this).addClass('select');
                $('.display').css('display', 'block');
                $('.display p').html($(this).next().html());
            });
            div_child.appendChild(button_child);
            div_child.appendChild(text_child);
            if(i<10){
                //div_child.appendChild(button_child);
                //div_child.appendChild(text_child);
                div.appendChild(div_child);
            }else{
                //button_child.css("animation","bounce-down1 2.3s linear infinite;");
                //$(".button_child").css("-webkit-animation","bounce-down1 2.3s linear infinite;");
                //var div_11 = document.createElement('div');
                //div_11.className="div_11";
                if(i%2==0) {
                    //div_child.style = "animation:bounce-down1 2.3s linear infinite;-webkit-animation:bounce-down1 2.3s linear infinite;";
                    div_11.appendChild(div_child);
                }else{
                    //div_child.style = "animation:bounce-down0 2.3s linear infinite;-webkit-animation:bounce-down0 2.3s linear infinite;";
                    div_11.appendChild(div_child);
                }
            }
        }
        div.appendChild(div_11);
        div.appendChild(button);
        $('.ok').click(function () {
            setCookie('childOpenid', $('.select').val(), 1);
            $('.fourPoint').css('display', 'block');
            $('.children').css('display', 'none');
            $('.display').css('display', 'none');
            $('.head').css('display', 'block');
        });
        $('.p5').css("display", "block").click(function () {
            window.location.href = "../../common_fourPoint.html";
        });
        $('.p1').click(function () {
            window.location.href = "../../positiveTest.html";
        });
        $('.p2').click(function () {
            window.location.href = "../../watchForm.html";
        });
        $('.p3').click(function () {
            window.location.href = "../../picture.html";
        });
        $('.p4').click(function () {
            window.location.href = "../../newFind.html";
        });
    }
    $('.p6').click(function () {
        window.location.href = "../../set.html";
    });
}
});