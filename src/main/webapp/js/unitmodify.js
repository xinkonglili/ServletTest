var unitName = null;
var unitCode = null;
var userDepartmentId = null;
var saveBtn = null;
var backBtn = null;

$(function(){
    unitName = $("#unitName");
    unitCode = $("#unitCode");
    userDepartmentId = $("#userDepartmentId");
    saveBtn = $("#save");
    backBtn = $("#back");

    unitRole.next().html("*");
    unitCode.next().html("*");
    userDepartmentId.next().html("*");


    $.ajax({
        type:"GET",//请求类型
        url:path+"/jsp/user.do",//请求的url
        data:{method:"getunitlist"},//请求参数
        dataType:"json",//ajax接口（请求url）返回的数据类型
        success:function(data){//data：返回数据（json对象）
            if(data != null){
                var rid = $("#rid").val();
                unitCode.html("");
                var options = "<option value=\"0\">请选择</option>";
                for(var i = 0; i < data.length; i++){
                    if(rid != null && rid != undefined && data[i].unitCode == rid ){
                        options += "<option selected=\"selected\" value=\""+data[i].unitCode+"\" >"+data[i].unitName+"</option>";
                    }else{
                        options += "<option value=\""+data[i].unitCode+"\" >"+data[i].unitName+"</option>";
                    }

                    var json = JSON.stringify(data[i]);
                    console.log("json----->"+json)
                }
                unitCode.html(options);
            }
        },
        error:function(data){//当访问时候，404，500 等非200的错误状态码
            validateTip(unitCode.next(),{"color":"red"},imgNo+" 获取用户角色列表error",false);
        }
    });


    $.ajax({
        type:"GET",//请求类型
        url:path+"/jsp/user.do",//请求的url
        data:{method:"getdepartmentlist"},//请求参数
        dataType:"json",//ajax接口（请求url）返回的数据类型
        success:function(data){//data：返回数据（json对象）
            if(data != null){
                var rid = $("#rid1").val();
                userDepartmentId.html("");
                var options = "<option value=\"1\">请选择</option>";
                for(var i = 0; i < data.length; i++){
                    if(rid != null && rid != undefined && data[i].departmentNameId == rid1 ){
                        options += "<option selected=\"selected\" value=\""+data[i].departmentNameId+"\" >"+data[i].departmentName+"</option>";
                    }else{
                        options += "<option value=\""+data[i].departmentNameId+"\" >"+data[i].departmentName+"</option>";
                    }
                }
                userDepartmentId.html(options);
            }
        },
        error:function(data){//当访问时候，404，500 等非200的错误状态码
            validateTip(userDepartmentId.next(),{"color":"red"},imgNo+" 获取用户角色列表error",false);
        }
    });


    unitName.on("focus",function(){
        validateTip(unitName.next(),{"color":"#666666"},false);
    }).on("blur",function(){
        if(unitName.val() != null && unitName.val().length > 1
            && unitName.val().length < 10){
            validateTip(unitName.next(),{"color":"green"},imgYes,true);
        }else{
            validateTip(unitName.next(),{"color":"red"},imgNo+" 单位名输入的不符合规范，请重新输入",false);
        }

    });


    saveBtn.on("click",function(){
        unitName.blur();
        unitCode.blur();
        userDepartmentId.blur();
        //userRole.blur();
        if(unitName.attr("validateStatus") == "true"
            && unitCode.attr("validateStatus") == "true"
           ){
            if(confirm("是否确认要提交数据？")){
                $("#userForm").submit();
            }
        }
    });

    backBtn.on("click",function(){
        //alert("modify: "+referer);
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