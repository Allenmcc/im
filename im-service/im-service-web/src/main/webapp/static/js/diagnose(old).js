/**
 * Created by Administrator on 2016/9/30.
 */
$(document).ready(function(){
    //setCookie("role","p",1);
    //setCookie("childOpenid","cdc",-1);
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
    var child={openid:"",childOpenid:"",name:"",evaluationPerson:"",age:"",sex:"",myClass:"",school:"",organization:""};//用于获取历史信息要查询的值

    $('#title_txt').html("("+qs_id+"/18)&nbsp;单选题");
    $('#content_txt').html(qs_array[qs_id-1]);
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
            //alert(score);
            $("#q"+score[qs_id-1]).attr("checked",'true');
            $(".ts_mask").fadeOut(1000);
        }
    });

    $('#button_next').click(function(){
        if($('input:checked').val()==undefined){
            alert('请选择一项');
        }else {
            if(qs_id!=18) {
                score[qs_id - 1] = $('input:checked').val();
                qs_id++;
                $('input:checked').removeAttr('checked');
                //alert(pre_score[qs_id - 1]);
                $('#title_txt').html("("+qs_id+"/18)&nbsp;单选题");
                $('#content_txt').html(qs_array[qs_id-1]);
                $("#q" + score[qs_id - 1]).prop('checked',true);
                if (qs_id == 18) {
                    $(this).html("提交");
                }
            }else {
                if (one_click == 1) {
                    one_click=0;
                    score[qs_id - 1] = $('input:checked').val();
                    change(score);
                    $(".ts_mask2").fadeIn(1000);
                    $.ajax({
                        url: '../starsea/diagnose/addStudyForm',
                        data: {
                            openId: child.openid,
                            childOpenId: child.childOpenid,
                            myName: child.name,
                            age: child.age,
                            sex: child.sex,
                            myClass: child.myClass,
                            school: child.school,
                            organization: child.organization,
                            evaluationPerson: child.evaluationPerson,
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
                                setCookie("scoreYs",data['msg']['msg']['scoreYs'],0.1);
                                setCookie("scoreBz",data['msg']['msg']['scoreBz'],0.1);
                                setCookie("scoreTotal",data['msg']['msg']['scoreTotal'],0.1);
                                setCookie("range",data['msg']['msg']['range'],0.1);
                                setCookie("childrenNumber",data['msg']['msg']['chidrenNumber'],0.1);
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
            }
        }});
    $('#button_pre').click(function(){
        if(qs_id==18){
            $('#button_next').html("下一题");
        }
        if(qs_id!=1){
            score[qs_id - 1] = $('input:checked').val();
            qs_id--;
            $('input:checked').removeAttr('checked');
            $('#title_txt').html("("+qs_id+"/18)&nbsp;单选题");
            $('#content_txt').html(qs_array[qs_id-1]);
            $("#q" +score[qs_id - 1]).prop('checked',true);
        }
    });
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