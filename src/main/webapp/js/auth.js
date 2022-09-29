$(function(){
    //刷新验证码
    $("#checkCode").click(function(){
        $("#loginCode").val('');
        $("#imgVcode").attr("src", getContextPath()+"/user/checkCode?dt="+new Date().getTime());
    });
    $("#userName").keydown(function(){
        $("#userEr").html("");$("#pswdEr").html("");
    });
    $("#pswd").keydown(function(){
        $("#userEr").html("");$("#pswdEr").html("");
    });
});
function on_submit(){
    if($("#userName").val() == "") {
        $("#userEr").html("请输入用户名");
        //$("#userName").focus();
        return false;
    }
    else if($("#pswd").val() == "") {
        $("#pswdEr").html("请输入密码");
        //$("#pswd").focus();
        return false;
    }
    else if($("#loginCode").val() == "") {
        $("#codeEr").html("请输入验证码");
        //$("#loginCode").focus();
        return false;
    }
}