/**
 * Created by Administrator on 2016/10/5.
 */
$(document).ready(function(){
    //setCookie("role","p",1);
    //setCookie("childOpenid","cdc",-1);

    var role=getCookie("role");
    if(role=="t"){
        $(".t_pic img").attr("src","static/image/teacher.jpg");
    }else{
        $(".t_pic img").attr("src","static/image/parent.jpg");
    }

    var one_click = 1;
    var qs_id = 1;//问题的编号
    var qs_array = new Array("尝试将正学习的知识与自身经验联系起来",
        "阅读中，能找到要点"
        ,"考试镇静，该做对的题目基本都能做对",
        "上课常走神，不能认真听老师在讲什么",
        "会通过联系相关情境去学习新知识、新原理",
        "考试、写作文或做作业时，常因不能理解老师的意思而出错",
        "考试时，常常因担心考砸而不能专心考试",
        "课堂上，全神贯注听课或做作业",
        "学习不得诀窍，比如不会做标记、编写小标题等",
        "对于名词、语法规则、公式等，通过理解来记忆，不死记硬背",
        "复习充分，考试时不慌不忙",
        "学习时，专心致志，心无旁骛",
        "学习时，不能将所学内容各要点联系起来",
        "知道如何学习不同的课程",
        "学习时情绪紧张",
        "学习时，不能集中注意力",
        "通过画图或示意图等帮助理解正在学习的内容",
        "做作业时，常迷失于细节，记不住主旨");//用于存问题的数组
    //var pre_score = new Array();//用于获取上次记录的值
    var score = new Array();//用于记录学生的成绩
    var child={openid:"",childOpenid:"",name:"",teacher:"",myClass:"",school:"",organization:""};//用于获取历史信息要查询的值
    $(".qd").click(function(){
        $(".ts_mask").fadeOut(1000);
    });
    $(".ts_mask").fadeIn(1000);//等待获取上次数据

    judgeCookie(child);
    child.openid=getCookie("openid");
    child.childOpenid=getCookie("childOpenid");
    if(child.childOpenid==false){
        child.childOpenid="none";
    }


    var div=document.querySelector('.q');
    for(var i=1;i<=qs_array.length;i++) {
        var div_c = document.createElement('div');
        div_c.className="div_c";
        var question = document.createElement('p');
        question.className="question";
        question.innerHTML=i+"."+qs_array[i-1];
        var div1 = document.createElement('div');
        div1.className="r_l_1";
        var div2 = document.createElement('div');
        div2.className="r_l_2";
        var div3 = document.createElement('div');
        div3.className="r_l_3";
        var div4 = document.createElement('div');
        div4.className="r_l_4";
        var div5 = document.createElement('div');
        div5.className="r_l_5";
        var r1 = document.createElement('input');
        r1.className="radio_c";
        r1.type="radio";
        r1.name="q"+i;
        r1.value="1";
        r1.id=i+"_"+"r1";
        var r2 = document.createElement('input');
        r2.className="radio_c";
        r2.type="radio";
        r2.name="q"+i;
        r2.value="2";
        r2.id=i+"_"+"r2";
        var r3 = document.createElement('input');
        r3.className="radio_c";
        r3.type="radio";
        r3.name="q"+i;
        r3.value="3";
        r3.id=i+"_"+"r3";
        var r4 = document.createElement('input');
        r4.className="radio_c";
        r4.type="radio";
        r4.name="q"+i;
        r4.value="4";
        r4.id=i+"_"+"r4";
        var r5 = document.createElement('input');
        r5.className="radio_c";
        r5.type="radio";
        r5.name="q"+i;
        r5.value="5";
        r5.id=i+"_"+"r5";
        var l1 = document.createElement('label');
        l1.className="label_c";
        l1.setAttribute("for",i+"_"+"r1");
        l1.innerHTML="非常不符合<br>";
        var l2 = document.createElement('label');
        l2.className="label_c";
        l2.setAttribute("for",i+"_"+"r2");
        l2.innerHTML="比较不符合<br>";
        var l3 = document.createElement('label');
        l3.className="label_c";
        l3.setAttribute("for",i+"_"+"r3");
        l3.innerHTML="中立<br>";
        var l4 = document.createElement('label');
        l4.className="label_c";
        l4.setAttribute("for",i+"_"+"r4");
        l4.innerHTML="比较符合<br>";
        var l5 = document.createElement('label');
        l5.className="label_c";
        l5.setAttribute("for",i+"_"+"r5");
        l5.innerHTML="非常符合<br>";

        div1.appendChild(r1);
        div1.appendChild(l1);
        div2.appendChild(r2);
        div2.appendChild(l2);
        div3.appendChild(r3);
        div3.appendChild(l3);
        div4.appendChild(r4);
        div4.appendChild(l4);
        div5.appendChild(r5);
        div5.appendChild(l5);

        div_c.appendChild(question);
        div_c.appendChild(div1);
        div_c.appendChild(div2);
        div_c.appendChild(div3);
        div_c.appendChild(div4);
        div_c.appendChild(div5);
        div.appendChild(div_c);
        if(i==qs_array.length) {
            var button = document.createElement('button');
            button.innerHTML = "提交";
            if(role=="t"){
                button.className = "submit teacherBackground";
            }else{
                button.className="submit parentBackground";
            }
            button.addEventListener("click", function () { //设置事件
                if ($("input:checked").size() == qs_array.length) {
                    if (one_click == 1) {
                        one_click = 0;
                        for (var i = 0; i < qs_array.length; i++) {
                            score[i] = $("input[name='q" + (i + 1) + "']:checked").val();
                            //alert(score[i]);
                        }
                        change(score);
                        $(".ts_mask2").fadeIn(1000);
                        $.ajax({
                            url: '../starsea/diagnose/addStudyForm',
                            data: {
                                openId: child.openid,
                                childOpenId: child.childOpenid,
                                myName: child.name,
                                teacher:child.teacher,
                                evaluationTime: getNowFormatDate(),
                                hc: score
                            },
                            type: 'post',
                            cache: false,
                            dataType: 'json',
                            success: function (data) {
                                console.log(data);
                                if (data.code == 200) {
                                    $(".ts_mask2").fadeOut(1000);
                                    setCookie("scoreYs", data['msg']['msg']['scoreYs'], 0.1);
                                    setCookie("scoreBz", data['msg']['msg']['scoreBz'], 0.1);
                                    setCookie("scoreTotal", data['msg']['msg']['scoreTotal'], 0.1);
                                    setCookie("range", data['msg']['msg']['range'], 0.1);
                                    setCookie("childrenNumber", data['msg']['msg']['chidrenNumber'], 0.1);
                                    //setCookie("result",data['msg']['msg']['result'],0.1);
                                    //setCookie("comment",data['msg']['msg']['comment'],0.1);
                                    window.location.href = '../../diagnoseScore.html';
                                }
                                else {
                                    alert("查询失败: " + data.msg);
                                }
                            },
                            error: function () {
                                alert("服务器处理异常");
                            }
                        });
                    }
                } else {
                    alert("请选完");
                }
            });
            div.appendChild(button);

            var button2 = document.createElement('button');
            button2.innerHTML = "返回";
            if(role=="t"){
                button2.className = "back teacherBackground";
            }else{
                button2.className="back parentBackground";
            }
            button2.addEventListener("click", function () { //设置事件
                window.location.href="positiveTest.html";
            });
            div.appendChild(button2);
        }
    }

    $.ajax({
        type: "GET",
        url: "../starsea/diagnose/getStudyFormByOpenId",
        data: {
            openId:child.openid,
            childOpenId:child.childOpenid
        },
        dataType: "json",
        success:function(data){ //成功的处理函数
            score = data['msg']['msg']['now_score'];
            if(score==null){
                score=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
            }else{
                score[3]=6-score[3];
                score[5]=6-score[5];
                score[6]=6-score[6];
                score[8]=6-score[8];
                score[12]=6-score[12];
                score[14]=6-score[14];
                score[15]=6-score[15];
                score[17]=6-score[17];
            }
            for(var i=0;i<qs_array.length;i++){
                $("#"+(i+1)+"_r"+score[i]).prop('checked',true);
            }
            $(".ts_mask").fadeOut(1000);
        }
    });
    //$(".q_submit").click(function(){
    //    //alert(1);
    //    if($("input:checked").size()==qs_array.length) {
    //        if (one_click == 1) {
    //            one_click = 0;
    //            for (var i = 0; i < qs_array.length; i++) {
    //                score[i] = $("input[name='q" + (i + 1) + "']:checked").val();
    //                //alert(score[i]);
    //            }
    //            change(score);
    //            $(".ts_mask2").fadeIn(1000);
    //            $.ajax({
    //                url: '../starsea/diagnose/addStudyForm',
    //                data: {
    //                    openId: child.openid,
    //                    childOpenId: child.childOpenid,
    //                    myName: child.name,
    //                    age: child.age,
    //                    sex: child.sex,
    //                    myClass: child.myClass,
    //                    school: child.school,
    //                    organization: child.organization,
    //                    evaluationPerson: child.evaluationPerson,
    //                    evaluationTime: getNowFormatDate(),
    //                    hc: score
    //                },
    //                type: 'post',
    //                cache: false,
    //                dataType: 'json',
    //                success: function (data) {
    //                    console.log(data);
    //                    if (data.code == 200) {
    //                        $(".ts_mask2").fadeOut(1000);
    //                        setCookie("scoreYs", data['msg']['msg']['scoreYs'], 0.1);
    //                        setCookie("scoreBz", data['msg']['msg']['scoreBz'], 0.1);
    //                        setCookie("scoreTotal", data['msg']['msg']['scoreTotal'], 0.1);
    //                        setCookie("range", data['msg']['msg']['range'], 0.1);
    //                        setCookie("childrenNumber", data['msg']['msg']['chidrenNumber'], 0.1);
    //                        //setCookie("result",data['msg']['msg']['result'],0.1);
    //                        //setCookie("comment",data['msg']['msg']['comment'],0.1);
    //                        window.location.href = '../../diagnoseScore.html';
    //                    }
    //                    else {
    //                        alert("查询失败: " + data.msg);
    //                    }
    //                },
    //                error: function () {
    //                    alert("服务器处理异常");
    //                }
    //            });
    //        }
    //    }else{
    //        alert("请选完");
    //    }
    //});
    var color=getCookie("bgColor");
        $(".head").css("backgroundColor",color);
        $(".back").css("backgroundColor",color);
        $(".submit").css("backgroundColor",color);
});

function change(array){
    array[3]=6-array[3];
    array[5]=6-array[5];
    array[6]=6-array[6];
    array[8]=6-array[8];
    array[12]=6-array[12];
    array[14]=6-array[14];
    array[15]=6-array[15];
    array[17]=6-array[17];
}