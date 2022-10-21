var unitCode = null;
var unitName = null;
var unitAddress = null;
var unitPhone = null;
var unitFax = null;;
var DepartmentId = null;;
var addUnitBtn = null;
var backBtn = null;




$(function(){
    unitCode = $("#unitCode");
    unitName = $("#unitName");
    unitAddress = $("#unitAddress");
    unitPhone = $("#unitPhone");
    unitFax = $("#unitFax");
    DepartmentId = $("#DepartmentId");
    addUnitBtn  = $("#addUnit");
    backBtn = $("#back");
    //初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
    unitCode.next().html("*");
    unitName.next().html("*");
    unitAddress.next().html("*");
    unitPhone.next().html("*");
    unitFax.next().html("*");


    $.ajax({
        type:"GET",//请求类型
        url:path+"/jsp/user.do",//请求的url
        data:{method:"getdepartmentlist"},//请求参数
        dataType:"json",//ajax接口（请求url）返回的数据类型
        success:function(data){//data：返回数据（json对象）
            if(data != null){
                DepartmentId.html("");
                var options = "<option value=\"0\">请选择</option>";
                for(var i = 0; i < data.length; i++){
                    var json = JSON.stringify(data[i]);
                    console.log("data[i]------>"+json)

                    options += "<option  value=\""+data[i].departmentNameId +"-"+data[i].departmentName+"\">"+data[i].departmentName+"</option>";
                }
                DepartmentId.html(options);
            }
        },
        error:function(data){//当访问时候，404，500 等非200的错误状态码
            validateTip(DepartmentId.next(),{"color":"red"},imgNo+" 获取用户角色列表error",false);
        }
    });

    unitCode.bind("focus",function(){
        validateTip(unitCode.next(),{"color":"#666666"},"* 用户名长度必须是大于1小于10的字符",false);
    }).bind("blur",function(){
        if(unitCode.val() != null && unitCode.val().length > 1
            && unitCode.val().length < 10){
            validateTip(unitCode.next(),{"color":"green"},imgYes,true);
        }else{
            validateTip(unitCode.next(),{"color":"red"},imgNo+" 用户名输入的不符合规范，请重新输入",false);
        }

    });

    unitName.bind("focus",function(){
        validateTip(unitName.next(),{"color":"#666666"},false);
    }).bind("blur",function(){
        if(unitName.val() != null && unitName.val().length > 1
            && unitName.val().length < 10){
            validateTip(unitName.next(),{"color":"green"},imgYes,true);
        }else{
            validateTip(unitName.next(),{"color":"red"},imgNo+" 用户名输入的不符合规范，请重新输入",false);
        }

    });



    unitAddress.bind("focus",function(){
        validateTip(unitAddress.next(),{"color":"#666666"},"* 用户名长度必须是大于1小于10的字符",false);
    }).bind("blur",function(){
        if(unitAddress.val() != null && unitAddress.val().length > 1
            && unitAddress.val().length < 10){
            validateTip(unitAddress.next(),{"color":"green"},imgYes,true);
        }else{
            validateTip(unitAddress.next(),{"color":"red"},imgNo+" 用户名输入的不符合规范，请重新输入",false);
        }

    });

    unitFax.bind("focus",function(){
        validateTip(unitFax.next(),{"color":"#666666"},"* 用户名长度必须是大于1小于10的字符",false);
    }).bind("blur",function(){
        if(unitFax.val() != null && unitFax.val().length > 1
            && unitFax.val().length < 10){
            validateTip(unitFax.next(),{"color":"green"},imgYes,true);
        }else{
            validateTip(unitFax.next(),{"color":"red"},imgNo+" 用户名输入的不符合规范，请重新输入",false);
        }

    });

    unitPhone.bind("focus",function(){
        validateTip(unitPhone.next(),{"color":"#666666"},"* 用户名长度必须是大于1小于10的字符",false);
    }).bind("blur",function(){
        if(unitPhone.val() != null && unitPhone.val().length > 1
            && unitPhone.val().length < 10){
            validateTip(unitPhone.next(),{"color":"green"},imgYes,true);
        }else{
            validateTip(unitPhone.next(),{"color":"red"},imgNo+" 用户名输入的不符合规范，请重新输入",false);
        }

    });


    addUnitBtn .bind("click",function(){
        if(unitCode.attr("validateStatus") != "true"){
            unitCode.blur();
        }else if(unitName.attr("validateStatus") != "true"){
            unitName.blur();
        }else if(unitAddress.attr("validateStatus") != "true"){
            unitAddress.blur();
        } else if(unitFax.attr("validateStatus") != "true"){
            unitFax.blur();
        }else if(unitPhone.attr("validateStatus") != "true"){
            unitPhone.blur();
        }else{
            if(confirm("是否确认提交数据")){
                $("#userForm").submit();
            }
        }
    });

    backBtn.on("click",function(){
        if(referer != undefined
            && null != referer
            && "" != referer
            && "null" != referer
            && referer.length > 4){
            window.location.href = referer;
        }else{
            history.back(-1);
        }
    });
});