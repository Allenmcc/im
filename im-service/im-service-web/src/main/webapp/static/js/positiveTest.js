/**
 * Created by Administrator on 2016/9/3.
 */
$(document).ready(function () {
    var flag = getCookie("p_t");//flag标志角色  p家长 t老师
    if (flag =="p") {//家长
        $('.p1').click(function(){
            window.location.href="../../diagnoseForm.html";
        });
        $('.p2').click(function(){
            window.location.href="../../parentTest.html";
        });
        $('.p3').click(function(){
            window.location.href="../../keyPoints.html";
        });
    }else{//老师

    }
});