<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>系统登录 - 管理系统</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" />
    <script type="text/javascript">
	/* if(top.location!=self.location){
	      top.location=self.location;
	 } */
    </script>
</head>
<body class="login_bg">
    <section class="loginBox">
        <header class="loginHeader">
            <h1>用户管理系统</h1>
        </header>
        <section class="loginCont">
	        <form class="loginForm" action="${pageContext.request.contextPath }/login.do"  name="actionForm" id="actionForm"  method="post" >
				<div class="info">${error }</div>
				<div class="inputbox">
                    <label >用户名：</label>
					<input type="text" class="input-text" id="userCode" name="userCode" placeholder="请输入用户名" required/>
				</div>	
				<div class="inputbox">
                    <label >密码：</label>
                    <input type="password" id="userPassword" name="userPassword" placeholder="请输入密码" required/>
                </div>

                <div class="inputboxId">
                    <label >验证码：</label>
                    <%--Math.random()防止有缓存--%>
                    <input type="password" id="userIdCode" name="userIdCode" placeholder="请输入验证码" required/><img src="code" style="padding: 4px; margin: -15px;margin-left: 4px;" onclick="this.src='code?'+Math.random();"><br/><br/>
                </div>




                <div class="subBtn">
					
                    <input type="submit" value="登录"/>
                </div>	
			</form>
        </section>
    </section>
</body>
</html>
