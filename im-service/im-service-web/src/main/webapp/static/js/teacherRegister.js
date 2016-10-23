$(document).ready(function(){
    $.ajax({
        url: '../starsea/user/queryTeacherRegister',
        type: 'post',
        cache: false,
        dataType: 'json',
        success: function (data) {
            if (data.code == 200) {
                var content=data['msg']['msg'];
                for(var i=0;i<content.length;i++){
                    var tr=document.createElement("tr");
                    var td1 = document.createElement('td');
                    td1.innerHTML=content[i].id;
                    var td2 = document.createElement('td');
                    td2.innerHTML=content[i].openId;
                    var td3 = document.createElement('td');
                    td3.innerHTML=content[i].teacherName;
                    var td4 = document.createElement('td');
                    td4.innerHTML=content[i].teacherSchool;
                    var td5 = document.createElement('td');
                    td5.innerHTML=content[i].teacherCommand;
                    var td6 = document.createElement('td');
                    td6.innerHTML=content[i].createTime;
                    var td7 = document.createElement('td');
                    var btn1=document.createElement('button');
                    btn1.className="yes";
                    btn1.setAttribute("click","0");
                    btn1.innerHTML="通过";
                    btn1.addEventListener("click", function () {
                        $(this).css("opacity","1");
                        $(this).attr("click","1");
                        $(this).next().css("opacity","0.4");
                        $(this).next().attr("click","0");
                    });
                    var btn2=document.createElement('button');
                    btn2.className="no";
                    btn2.setAttribute("click","0");
                    btn2.innerHTML="不通过";
                    btn2.addEventListener("click", function () {
                        $(this).css("opacity","1");
                        $(this).attr("click","1");
                        $(this).prev().css("opacity","0.4");
                        $(this).prev().attr("click","0");
                    });
                    var btn3=document.createElement('button');
                    btn3.className="reset";
                    btn3.innerHTML="重置";
                    btn3.addEventListener("click", function () {
                        $(this).prev().css("opacity","0.4");
                        $(this).prev().attr("click","0");
                        $(this).prev().prev().css("opacity","0.4");
                        $(this).prev().prev().attr("click","0");
                    });
                    td7.appendChild(btn1);
                    td7.appendChild(btn2);
                    td7.appendChild(btn3);
                    tr.appendChild(td1);
                    tr.appendChild(td2);
                    tr.appendChild(td3);
                    tr.appendChild(td4);
                    tr.appendChild(td5);
                    tr.appendChild(td6);
                    tr.appendChild(td7);
                    $("tbody").append(tr);
                }
            }
        },
        error: function () {
            alert("服务器处理异常");
        }
    });

    $(".submit").click(function(){
        var deleteRegisterOpenId=[];//用来存储 通过或者不通过（注册表中相应记录都要删除）的openid
        var yesTeachers=[];//以一维数组的形式将 通过审核的老师 一次性发给后台
        var n=0;
        var m=0;
        //添加通过的信息
        $("tr").each(function(){
            if($(this).find(".yes").attr("click")=="1"){
                deleteRegisterOpenId[n]=$(this).children().eq(1).html();
                n++;
                //var con=[];
                for(var i=0;i<5;i++){
                    var text=$(this).children().eq(i+1).html();
                    //alert(text);
                    //con[i]=text;
                    yesTeachers[m]=text;
                    m++;
                }
                //yesTeachers[n]=con;
            }
            if($(this).find(".no").attr("click")=="1"){
                deleteRegisterOpenId[n]=$(this).children().eq(1).html();
                n++;
            }
        });
        //alert(deleteRegisterOpenId);
        $.ajax({
            url: '../starsea/user/deleteTeacherRegister',
            data:{
                registerOpenIds:deleteRegisterOpenId
            },
            type: 'post',
            cache: false,
            dataType: 'json',
            success: function (data) {
                if (data.code == 200) {
                    $.ajax({
                        url: '../starsea/user/passTeacherRegister',
                        data:{
                            yesTeachers:yesTeachers
                        },
                        type: 'post',
                        cache: false,
                        dataType: 'json',
                        success: function (data) {
                            if (data.code == 200) {
                                window.location.href="teacherRegister.html";
                            }
                        }
                    });
                }
            }
        });

    });
});