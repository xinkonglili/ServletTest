<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>

<div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>用户管理页面 >> 用户添加页面</span>
        </div>
        <div class="providerAdd">
            <form id="userForm" name="userForm"  method="post" action="${pageContext.request.contextPath }/jsp/user.do" >
                <input type="hidden" name="method" value="add">
                <div>
                    <label for="userCode">用户编码：</label>
                    <input type="text" name="userCode" id="userCode" value=""> 
					<!-- 放置提示信息 -->
					<font color="red"></font>
                </div>
                <div>
                    <label for="userName">用户名称：</label>
                    <input type="text" name="userName" id="userName" value=""> 
					<font color="red"></font>
                </div>
                <div>
                    <label for="userPassword">用户密码：</label>
                    <input type="password" name="userPassword" id="userPassword" value=""> 
					<font color="red"></font>
                </div>
--%>
                <div>
                    <label >单位角色：</label>
                    <!-- 列出所有的单位角色分类--来自哪个公司 -->
					<select name="unitRole" id="unitRole"></select>
                </div>

                <div>
                    <label >部门角色：</label>
                    <!-- 列出所有的单位角色分类--来自哪个部门，userDepartmentId是部门表里面的部门号对应的id -->
                    <select name="DepartmentId" id="DepartmentId"></select>
                </div>

                     <label >上传图片：</label>
                     <input type="file" name="uploadmsg" value="上传图片">


                <div class="providerAddBtn">
                    <input type="submit" name="add" id="add" value="保存" >
					<input type="cancel" id="back" name="back" value="返回" >
                </div>

            </form>
        </div>
</div>
</section>
<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/useradd.js"></script>
