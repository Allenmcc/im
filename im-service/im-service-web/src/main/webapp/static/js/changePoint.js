/**
 * Created by Administrator on 2016/10/16.
 */
$(document).ready(function () {
    var color=getCookie("bgColor");

        $("body").css("backgroundColor",color);

    var enerySelect=getCookie("enery");
    var flag=[1,1,1,1,1,1,1];//用于判断哪几个是没有被选择的  1为未选择
    var n=1;
    //1换1
    var fS=0;//用来标志 已选择模块的 选中情况
    var fN=0;//用来标志 未选择模块的 选中情况
    var result="";//最后更换的结果
    for(var i=0;i<enerySelect.length;i++){
        //alert(enerySelect);
        //alert(enerySelect.charAt(i));
        switch (enerySelect.charAt(i)){
            case "1":
                //alert(1);
                flag[0]=0;
                $(".b"+(i+1)+" img").attr("src","static/image/category7.jpg");
                $(".b"+(i+1)+" p").html("学习策略");
                $(".b"+(i+1)).attr('val','1');
                break;
            case "2":
                //alert(2);
                flag[1]=0;
                $(".b"+(i+1)+" img").attr("src","static/image/category6.jpg");
                $(".b"+(i+1)+" p").html("学习认知");
                $(".b"+(i+1)).attr('val','2');
                break;
            case "3":
                //alert(3);
                flag[2]=0;
                $(".b"+(i+1)+" img").attr("src","static/image/category3.jpg");
                $(".b"+(i+1)+" p").html("自主力");
                $(".b"+(i+1)).attr('val','3');
                break;
            case "4":
                //alert(4);
                flag[3]=0;
                $(".b"+(i+1)+" img").attr("src","static/image/category4.jpg");
                $(".b"+(i+1)+" p").html("专注力");
                $(".b"+(i+1)).attr('val','4');
                break;
            case "5":
                //alert(5);
                flag[4]=0;
                $(".b"+(i+1)+" img").attr("src","static/image/category5.jpg");
                $(".b"+(i+1)+" p").html("意志力");
                $(".b"+(i+1)).attr('val','5');
                break;
            case "6":
                //alert(6);
                flag[5]=0;
                $(".b"+(i+1)+" img").attr("src","static/image/category1.jpg");
                $(".b"+(i+1)+" p").html("情绪力");
                $(".b"+(i+1)).attr('val','6');
                break;
            case "7":
                //alert(7);
                flag[6]=0;
                $(".b"+(i+1)+" img").attr("src","static/image/category2.jpg");
                $(".b"+(i+1)+" p").html("人际力");
                $(".b"+(i+1)).attr('val','7');
                break;
        }
    }
    for(var i=0;i<flag.length;i++){
        if(flag[i]==1){
            switch (i+1){
                case 1:
                    $(".nb"+n+" img").attr("src","static/image/category7.jpg");
                    $(".nb"+n+" p").html("学习策略");
                    $(".nb"+n).attr('val','1');
                    n++;
                    break;
                case 2:
                    $(".nb"+n+" img").attr("src","static/image/category6.jpg");
                    $(".nb"+n+" p").html("学习认知");
                    $(".nb"+n).attr('val','2');
                    n++;
                    break;
                case 3:
                    $(".nb"+n+" img").attr("src","static/image/category3.jpg");
                    $(".nb"+n+" p").html("自主力");
                    $(".nb"+n).attr('val','3');
                    n++;
                    break;
                case 4:
                    $(".nb"+n+" img").attr("src","static/image/category4.jpg");
                    $(".nb"+n+" p").html("专注力");
                    $(".nb"+n).attr('val','4');
                    n++;
                    break;
                case 5:
                    $(".nb"+n+" img").attr("src","static/image/category5.jpg");
                    $(".nb"+n+" p").html("意志力");
                    $(".nb"+n).attr('val','5');
                    n++;
                    break;
                case 6:
                    $(".nb"+n+" img").attr("src","static/image/category1.jpg");
                    $(".nb"+n+" p").html("情绪力");
                    $(".nb"+n).attr('val','6');
                    n++;
                    break;
                case 7:
                    $(".nb"+n+" img").attr("src","static/image/category2.jpg");
                    $(".nb"+n+" p").html("人际力");
                    $(".nb"+n).attr('val','7');
                    n++;
                    break;
            }
        }
    }
    $(".buttonSelect").click(function(){
        if(fS==0&&$(this).attr('click')=='1'){
            $(this).css('opacity','0.4');
            $(this).attr('click','0');
            fS=1;
        }else if(fS==1&&$(this).attr('click')=='0'){
            $(this).css('opacity','1');
            $(this).attr('click','1');
            fS=0;
        }
    });
    $(".nobuttonSelect").click(function(){
        if(fN==0&&$(this).attr('click')=='0'){
            $(this).css('opacity','1');
            $(this).attr('click','1');
            fN=1;
        }else if(fN==1&&$(this).attr('click')=='1'){
            $(this).css('opacity','0.4');
            $(this).attr('click','0');
            fN=0;
        }
    });

    $(".submit").click(function(){
        if($("button[click='1']").size()!=5){
            alert("请在两栏中各选择一个进行一对一更换");
        }else {
            $("button[click='1']").each(function () {
                result += $(this).attr('val');
            });
            //alert(result);
            $.ajax({
                url: '../starsea/userenery/addUserEnery',
                data: {
                    openId: getCookie("openid"),
                    enerySelect: result,
                    evaluationTime: getNowFormatDate(),
                    title: "none",
                    customResult: "none"
                },
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function (data) {
                    console.log(data);
                    if (data.code == 200) {
                        //$(".ts_mask2").fadeOut(1000);
                        //alert("success");
                        //setCookie('enery',result,30);
                        setCookie('sevenCF', "B", 30);
                        setCookie('label_ejia', 'select', 30);
                        window.location.href = 'watchForm.html';
                    }
                },
                error: function () {
                    alert("服务器处理异常");
                    $(".ts_mask2").fadeOut(1000);
                }
            });
        }
    });

    $('.sChose').click(function(){
        $('.chose').css('display','none');
        $('.div_1').css('display','block');
    });

    $('.self').click(function(){
        $('.chose').css('display','none');
        $('.div_2').css('display','block');
        for(var i=0;i<enerySelect.length;i++){
            //alert(enerySelect);
            //alert(enerySelect.charAt(i));
            switch (enerySelect.charAt(i)){
                case "1":
                    $(".b"+(i+1)+"_2 img").attr("src","static/image/category7.jpg");
                    $(".b"+(i+1)+"_2 p").html("学习策略");
                    $(".b"+(i+1)+"_2").attr('val','1');
                    break;
                case "2":
                    $(".b"+(i+1)+"_2 img").attr("src","static/image/category6.jpg");
                    $(".b"+(i+1)+"_2 p").html("学习认知");
                    $(".b"+(i+1)+"_2").attr('val','2');
                    break;
                case "3":
                    $(".b"+(i+1)+"_2 img").attr("src","static/image/category3.jpg");
                    $(".b"+(i+1)+"_2 p").html("自主力");
                    $(".b"+(i+1)+"_2").attr('val','3');
                    break;
                case "4":
                    $(".b"+(i+1)+"_2 img").attr("src","static/image/category4.jpg");
                    $(".b"+(i+1)+"_2 p").html("专注力");
                    $(".b"+(i+1)+"_2").attr('val','4');
                    break;
                case "5":
                    $(".b"+(i+1)+"_2 img").attr("src","static/image/category5.jpg");
                    $(".b"+(i+1)+"_2 p").html("意志力");
                    $(".b"+(i+1)+"_2").attr('val','5');
                    break;
                case "6":
                    $(".b"+(i+1)+"_2 img").attr("src","static/image/category1.jpg");
                    $(".b"+(i+1)+"_2 p").html("情绪力");
                    $(".b"+(i+1)+"_2").attr('val','6');
                    break;
                case "7":
                    $(".b"+(i+1)+"_2 img").attr("src","static/image/category2.jpg");
                    $(".b"+(i+1)+"_2 p").html("人际力");
                    $(".b"+(i+1)+"_2").attr('val','7');
                    break;
            }
        }
    });
    $(".buttonSelect_2").click(function(){
        if(fN==0&&$(this).attr('click')=='0'){
            $(this).css('opacity','1');
            $(this).attr('click','1');
            fN=1;
        }else if(fN==1&&$(this).attr('click')=='1'){
            $(this).css('opacity','0.4');
            $(this).attr('click','0');
            fN=0;
        }
    });
    $('.submit_2').click(function(){
        if($(".div_2 button[click='1']").size()!=1){
            alert("请在选出一个进行更换");
        }else {
            var del=$(".div_2 button[click='1']").attr('val');
            //alert(del);
            var index=enerySelect.indexOf(del);
            //alert(index);
            result=enerySelect.substring(0,index)+enerySelect.substring(index+1,enerySelect.length);
            //alert(result);
            setCookie('custom',result,1);
            window.location.href="custom.html"
        }
    });
});