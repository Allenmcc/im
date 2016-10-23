/**
 * Created by Administrator on 2016/9/1.
 */
$(document).ready(function () {
    //setCookie("childOpenid","22",1);
    var log=[];
    var role=getCookie("role");
    var labels="";
    var color=getCookie("bgColor");
        $("body").css("backgroundColor",color);

    var openid=getCookie('openid');
    var childOpenid=getCookie('childOpenid');
    //alert(childOpenid);
    if(!childOpenid){
        childOpenid="none";
    }
    if(document.referrer.substring(document.referrer.length-17)=='picture_self.html'){
        //alert(11);
        childOpenid="none";
    }
    //alert(childOpenid);
        $.ajax({
        type: "GET",
        url: "../starsea/log/getUserLog",
        dataType: "json",
        data: {
            openId: openid,
            childOpenId:childOpenid
        },
        async: false,
        success: function (data) {
            if (data['msg']['msg'] != null) {
                var content = data['msg']['msg']['log'];//获取日志信息  生成一个数组 split content=['a','bbbbbbb','2016-09-01','c','ddddddd','2016-09-02']；
                var log1 = content.split('}');
                //alert(log1);
                for (var i = 0; i < log1.length-1; i++) {
                    var log2=log1[i].split('#');
                    log2[0]=log2[0].substring(1,log2[0].length);
                    //alert(log2[0]);
                    log[i] = {title: "", content: "", time: "",label:""};
                    log[i].title = log2[0];
                    log[i].content = log2[2];
                    log[i].time = log2[3];
                    log[i].label=log2[1];
                }
                //测试用例
                //var log_num = 3;
                //log = [{
                //    title: '长期吃手',
                //    content: '孩子一岁半开始，就因为吃手的问题和孩子纠缠不清。据家长讲，最初阻止孩子吃手，采用的是讲道理，告诉孩子手很脏，不能吃，他们感觉一岁半的孩子能听懂了。发现讲道理没用，采用打手的办法，轻打不起作用，就狠狠打。后来，负责照看孩子的奶奶拿出缝衣针，只要孩子的小手一放进嘴里，就用针扎一下，并把针挂到墙上，故意让孩子看到，但这也不能吓住孩子。再后来家长还给孩子手上抹辣椒水，每天24小时戴手套等各种办法，可是问题始终没能得到解决。',
                //    time: '2015-12-25'
                //}, {title: 'a', content: 'bbbb', time: '2015-12-31'}, {
                //    title: 'c',
                //    content: 'ddddd',
                //    time: '2016-01-01'
                //}];

                for (var i = 0; i < log.length; i++) {
                    var section = document.querySelector('section');
                    //创建元素
                    var div = document.createElement('div')
                    var div_img = document.createElement("div");
                    var div_content = document.createElement("div");
                    var img = document.createElement("img");
                    var h2 = document.createElement("h2");
                    var p = document.createElement("p");
                    var p2 = document.createElement("p");
                    var p3 = document.createElement("p");
                    var input_detail = document.createElement("input");
                    var span = document.createElement("span");
                    var input_delete = document.createElement("input");
                    //设置不同的属性
                    div.setAttribute('class', 'cd-timeline-block');
                    div_img.setAttribute('class', 'cd-timeline-img cd-movie');
                    div_content.setAttribute('class', 'cd-timeline-content');
                    img.src = "static/image/cd-icon-movie.svg";
                    img.alt = "Movie";
                    h2.innerHTML = log[i].title;
                    var l = 50;
                    if (log[i].content.length < 50) {
                        l = log[i].content.length;
                    }
                    p2.className="xiaoshi";
                    p.innerHTML = log[i].content.substring(0, l);
                    p2.innerHTML = log[i].content;
                    p3.className="logLabel";
                    p3.innerHTML="标签："+log[i].label;
                    input_detail.className = "cd-read-more";
                    input_detail.value = "查看详情";
                    input_detail.type = "button";
                    //input_detail.setAttribute('onclick','a()');
                    //$('.input_detail').click(function(){alert(11)});
                    //查看详情
                    input_detail.addEventListener('click', function () {
                        alert($(this).prev().html());
                    }, false);
                    span.className = "cd-date";
                    span.innerHTML = log[i].time;
                    input_delete.className = "cd-read-more";
                    input_delete.value = "删除";
                    input_delete.type = "button";
                    //删除事件
                    input_delete.addEventListener('click', function () {
                        var title = $(this).prev().prev().prev().prev().prev().prev().html();
                        var content = $(this).prev().prev().prev().html();
                        var time = $(this).prev().html();
                        var labels=$(this).prev().prev().prev().prev().prev().html().substring(3);
                        //alert(title);
                        //alert(content);
                        //alert(time);
                        $.ajax({
                            type: "POST",
                            url: "../starsea/log/deleteUserLog",
                            dataType: "json",
                            data: {
                                openId: openid,
                                childOpenId:childOpenid,
                                title: title,
                                content: content,
                                time: time,
                                labels:labels
                            },
                            success: function (data) {
                                window.location.reload();
                            }
                        });
                        //window.location.reload();//刷新当前页面
                    }, false);
                    div_img.appendChild(img);
                    div_content.appendChild(h2)
                    div_content.appendChild(p3);;
                    div_content.appendChild(p);
                    div_content.appendChild(p2);
                    div_content.appendChild(input_detail);
                    div_content.appendChild(span);
                    div_content.appendChild(input_delete);
                    div.appendChild(div_img);
                    div.appendChild(div_content);
                    section.appendChild(div);
                }
            }
        }
    });
        $('.submit').click(function(){
            var title=$('#new_title').val();
            var content=$('#new_content').val();
            labels=labels.substring(0,labels.length-1);
            $.ajax({
                type: "POST",
                url: "../starsea/log/addUserLog",
                dataType: "json",
                data: {
                    openId:openid,
                    childOpenId:childOpenid,
                    title:title,
                    content:content,
                    time:getNowFormatDate(),
                    labels:labels
                },
                success: function (data) {
                    window.location.reload();//刷新当前页面
                }
            });
        });

    $("#addlabel").click(function(){
        if($(".iLabel").val().length>0) {
            var bt = document.createElement("button");
            bt.className = "lb";
            bt.innerHTML = $(".iLabel").val();
            labels+=$(".iLabel").val()+"、";
            $(".labels").append(bt);
            $(".iLabel").val("");
        }
    });
$(".n_back").click(function(){
   window.location.href=document.referrer;
});
$(".search_b").click(function(){
    var labels=$(".search").val();
    if(labels.length==0){
        $(".cd-timeline-block").css('display','block');
    }else {
        $(".cd-timeline-block").css('display','none');
        labels=labels.split(",");
        for(var i=0;i<labels.length;i++) {
            $(".logLabel").each(function () {
                if($(this).is(":hidden") ) { // 判断是否隐藏
                    if ($(this).html().indexOf(labels[i]) != -1) {
                        $(this).parent().parent().css('display', 'block');
                    }
                }
            });
        }
        $(".search").val("");
    }
});

    $('.new').click(function(){
        $('.div_1').css('display','none');
        $(".div_search").css('display','none');
        $('.div_2').css('display','block');
        $('#new_title').val('');
        $('#new_content').val('记录每天新的发现');
        $('#new_content').css('color','#999');
    });
    $('.back').click(function(){
        $('#new_title').val('');
        $('#new_content').val('记录每天新的发现');
        $('#new_content').css('color','#999');
        $('.div_1').css('display','block');
        $('.div_2').css('display','none');
        $(".div_search").css('display','block');
    });

    function getNowFormatDate() {//获取当前时间,yyyy-MM-dd
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    }
});


//function a(){
//    alert(1);
//}