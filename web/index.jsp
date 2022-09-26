<%--
  Created by IntelliJ IDEA.
  User: wllwo
  Date: 2022/9/23
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>login</title>

  <style>
    body {
      margin-top: 120px;
      margin-left: 350px;

    }
    div.form-group{
      padding: 4px;
    }
    div.col-sm-3{
      margin-left: 95px;
      padding: 2px;
    }
    div.col-sm-6{
      margin-left: 95px;
      margin-top: 10px;
    }
    div.col-sm-offset-2 col-sm-10{
      margin-left: 95px;
    }
    .carousel-inner .item img {
      width: 100%;
      height: 300px;
    }
    .container .row div {
      /* position:relative;
      float:left; */
    }

    element.style {
    }
    .container .row div {
    }
    div.col-sm-8 {
      color: black;
      font-size: larger;
      text-align: center;
      margin: -40px;
      padding: 45px;
    }
    font {
      background:#c0c0c0 ;
      color: #666;
      font-size: 22px;
      font-weight: normal;
      padding-right: 17px;
    }
  </style>
</head>

<body>
<div class="container" style="width:100%;height:460px;">
  <div class="row">
    <div class="col-md-5">
      <div style="width:400px;height: 200px;border:1px solid #E7E7E7;padding:35px 0 20px 35px;border-radius:5px;margin-top:60px;margin-left: 95px;background:beige ;">
        <form id="loginForm" class="form-horizontal" action="Login" method="post">

          <div class="form-group">
           <%-- <label for="username" class="col-sm-2 control-label">用户名</label>--%>

            <div class="col-sm-8">用户登录</div>


            <div class="col-sm-6">
              <!--表单要加上name属性，否则服务器得到不值-->
              <input type="text" class="form-control" name="username" id="username" placeholder="请输入用户名">
            </div>
          </div>
          <div class="form-group">
            <%--<label for="password" class="col-sm-2 control-label">密码</label>--%>
            <div class="col-sm-6">
              <input type="password" class="form-control" name="password" id="password"
                     placeholder="请输入密码">
            </div>
          </div>

          <div class="form-group">
            <%--<label for="verCode" class="col-sm-2 control-label">验证码</label>--%>
            <div class="col-sm-3">
              <input type="text" class="form-control" name="verCode" id="verCode" placeholder="请输入验证码">
              <img src="Encode" style="width: 70px; height: 25px; cursor: pointer;display: inline-block; "
                   onclick="changeImage(this)">
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">

            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10" style=" ">
              <input id="click" type="submit" width="100" value="登录" name="submit" border="0"
                     style="  height:30px;width:100px; margin-left: 115px;">
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
