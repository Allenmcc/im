$(document).ready(function () {
    var color=getCookie("bgColor");
    //if(color){
        $("body").css("backgroundColor",color);
        $(".col").each(function(){
           if($(this).attr("class").indexOf(color)!=-1){
               $("input").removeClass("select");
               $(this).addClass("select");
           }
        });
    //}
    //else{
    //    $("body").css("backgroundColor",'white');
    //    $(".lightgreen").addClass("select");
    //}
    $(".col").click(function(){
        $("input").removeClass("select");
        $(this).addClass("select");
        var bgColor=$(this).attr("class").split(" ")[0];
        $("body").css("backgroundColor",bgColor);
    });
    $(".back").click(function(){
        window.location.href="../../common_fourPoint.html";
    })
    $(".ensure").click(function(){
       setCookie("bgColor",$(".select").attr("class").split(" ")[0],10000);
        window.location.href="../../common_fourPoint.html";
    });
});