<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>
    <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>用户管理页面 >> 用户修改页面</span>
        </div>
        <div class="providerAdd">
        <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath }/jsp/user.do">
			<input type="hidden" name="method" value="modifyexe">
			<input type="hidden" name="uid" value="${user.id }"/>
			 <div>
                    <label for="userCode">用户编码：</label>
                    <input type="text" name="userCode" id="userCode" value="${user.userCode }">
					<font color="red"></font>
             </div>
            <div>
                <label for="userName">用户名称：</label>
                <input type="text" name="userName" id="userName" value="${user.userName }">
                <font color="red"></font>
            </div>

            <div>
                <label >所属部门：</label>
                <!-- 列出所有的部门角色分类 -->
                <input type="hidden" value="${user.userDepartmentId }" id="rid1" />
                <select name="userDepartmentId" id="userDepartmentId"></select>
                <font color="red"></font>
            </div>

            <div>
                <label >所属单位：</label>
                <!-- 列出所有的角色分类 -->
                <input type="hidden" value="${user.unitRole }" id="rid" />
                <select name="unitCode" id="unitCode"></select>
                <font color="red"></font>
            </div>

			 <div class="providerAddBtn">
                    <input type="submit" name="save" id="save" value="保存" />
                    <input type="button" id="back" name="back" value="返回"/>
                </div>
            </form>
        </div>
    </div>
</section>
<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/usermodify.js"></script>
