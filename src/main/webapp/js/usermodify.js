var userName = null;
var userCode = null;
var unitName = null;
var unitRole = null;
var saveBtn = null;
var backBtn = null;


$(function(){
	userName = $("#userName");
	unitName = $("#unitName");
	userCode = $("#userCode");
	unitRole = $("#unitRole");
	saveBtn = $("#save");
	backBtn = $("#back");
	
	userName.next().html("*");
	userCode.next().html("*");
	unitRole.next().html("*");
	
	
	$.ajax({
		type:"GET",//请求类型
		url:path+"/jsp/user.do",//请求的url
		data:{method:"getrolelist"},//请求参数
		dataType:"json",//ajax接口（请求url）返回的数据类型
		success:function(data){//data：返回数据（json对象）
			if(data != null){
				var rid = $("#rid").val();
				unitRole.html("");
				var options = "<option value=\"0\">请选择</option>";
				for(var i = 0; i < data.length; i++){
					if(rid != null && rid != undefined && data[i].id == rid ){
						options += "<option selected=\"selected\" value=\""+data[i].id+"\" >"+data[i].unitName+"</option>";
					}else{
						options += "<option value=\""+data[i].id+"\" >"+data[i].unitName+"</option>";
					}
				}
				unitRole.html(options);
			}
		},
		error:function(data){//当访问时候，404，500 等非200的错误状态码
			validateTip(unitRole.next(),{"color":"red"},imgNo+" 获取用户角色列表error",false);
		}
	});

	userCode.on("focus",function(){
		validateTip(userCode.next(),{"color":"#666666"},"* 用户编码的长度必须是大于1小于10的字符",false);
	}).on("blur",function(){
		if(userCode.val() != null && userCode.val().length > 1
			&& userCode.val().length < 10){
			validateTip(userCode.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(userCode.next(),{"color":"red"},imgNo+" 用户编码输入的不符合规范，请重新输入",false);
		}

	});

	userName.on("focus",function(){
		validateTip(userName.next(),{"color":"#666666"},"* 用户名长度必须是大于1小于10的字符",false);
	}).on("blur",function(){
		if(userName.val() != null && userName.val().length > 1
				&& userName.val().length < 10){
			validateTip(userName.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(userName.next(),{"color":"red"},imgNo+" 用户名输入的不符合规范，请重新输入",false);
		}

	});


	unitRole.on("focus",function(){
		validateTip(unitRole.next(),{"color":"#666666"},"* 请选择单位角色",false);
	}).on("blur",function(){
		if(unitRole.val() != null && unitRole.val() > 0){
			validateTip(unitRole.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(unitRole.next(),{"color":"red"},imgNo+" 请重新选择单位角色",false);
		}
		
	});
	
	saveBtn.on("click",function(){
		userName.blur();
		userCode.blur();
		unitRole.blur();
		//userRole.blur();
		if(userName.attr("validateStatus") == "true" 
			&& unitRole.attr("validateStatus") == "true"
			&& userCode.attr("validateStatus") == "true"
			&& userRole.attr("validateStatus") == "true"){
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