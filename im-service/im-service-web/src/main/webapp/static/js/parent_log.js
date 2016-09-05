/**
 * Created by Administrator on 2016/9/1.
 */
$(document).ready(function () {
    var log=[];
    var openid=getCookie('openid');
    $('#new').click(function(){

    });
    $.ajax({
        type: "GET",
        url: "../starsea/user/getUserByOpenId",
        dataType: "json",
        data: {
            openId: "o45t9wZx7eQo5VIB4nTY_76TCW4w"
        },
        async: false,
        success: function (data) {
            //var content=data['msg']['msg'];//获取日志信息  生成一个数组 split content=['a','bbbbbbb','2016-09-01','c','ddddddd','2016-09-02']；
            //var log_num=0;
            //for(var i=0;i<content.length;i+=3) {
            //    log[log_num]={title:"",content:"",time:""};
            //    log[log_num].title=content[i];
            //    log[log_num].content=content[i+1];
            //    log[log_num].time=content[i+2];
            //    log_num+=1;
            //}

            //测试用例
            var log_num=3;
            log=[{title:'长期吃手',content:'孩子一岁半开始，就因为吃手的问题和孩子纠缠不清。据家长讲，最初阻止孩子吃手，采用的是讲道理，告诉孩子手很脏，不能吃，他们感觉一岁半的孩子能听懂了。发现讲道理没用，采用打手的办法，轻打不起作用，就狠狠打。后来，负责照看孩子的奶奶拿出缝衣针，只要孩子的小手一放进嘴里，就用针扎一下，并把针挂到墙上，故意让孩子看到，但这也不能吓住孩子。再后来家长还给孩子手上抹辣椒水，每天24小时戴手套等各种办法，可是问题始终没能得到解决。',time:'2015-12-25'},{title:'a',content:'bbbb',time:'2015-12-31'},{title:'c',content:'ddddd',time:'2016-01-01'}];

            for(var i=0;i<log_num;i++){
                var section=document.querySelector('section');
                //创建元素
                var div = document.createElement('div')
                var div_img = document.createElement("div");
                var div_content = document.createElement("div");
                var img=document.createElement("img");
                var h2=document.createElement("h2");
                var p=document.createElement("p");
                var p2=document.createElement("p");
                var input_detail=document.createElement("input");
                var span=document.createElement("span");
                var input_delete=document.createElement("input");
                //设置不同的属性
                div.setAttribute('class', 'cd-timeline-block');
                div_img.setAttribute('class', 'cd-timeline-img cd-movie');
                div_content.setAttribute('class', 'cd-timeline-content');
                img.src="static/image/cd-icon-movie.svg";
                img.alt="Movie";
                h2.innerHTML=log[i].title;
                var l=50;
                if(log[i].content.length<50){
                    l=log[i].content.length;
                }
                p.innerHTML=log[i].content.substring(0,l);
                p2.innerHTML=log[i].content;
                p2.style="display:none";
                input_detail.className="cd-read-more";input_detail.value="查看详情";input_detail.type="button";
                //input_detail.setAttribute('onclick','a()');
                //$('.input_detail').click(function(){alert(11)});
                //查看详情
                input_detail.addEventListener('click',function(){
                    alert($(this).prev().html());
                },false);
                span.className="cd-date";span.innerHTML=log[i].time;
                input_delete.className="cd-read-more";input_delete.value="删除";input_delete.type="button";
                //删除事件
                input_delete.addEventListener('click',function(){
                    var title=$(this).prev().prev().html();
                    var content=$(this).prev().html();
                    var time=$(this).next().html();
                    $.ajax({
                        type: "GET",
                        url: "../starsea/user/getUserByOpenId",
                        dataType: "json",
                        data: {
                            openId:openid,
                            title:title,
                            content:content,
                            time:time
                        },
                        success: function (data) {

                        }
                    });
                    window.location.reload();//刷新当前页面
                },false);
                div_img.appendChild(img);
                div_content.appendChild(h2);
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
    });


    $('.new').click(function(){
        $('.div_1').css('display','none');
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
    });
    $('.submit').click(function(){
        var title=$('#new_title').val();
        var content=$('#new_content').val();
        $.ajax({
            type: "POST",
            url: "../starsea/",
            dataType: "json",
            data: {
                title:title,
                content:content,
                time:getNowFormatDate()
            },
            success: function (data) {
                window.location.reload();//刷新当前页面
            }
        });
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