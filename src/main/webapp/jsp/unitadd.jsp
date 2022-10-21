<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>单位管理页面 >> 单位添加页面</span>
    </div>
    <div class="providerAdd">
        <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath }/jsp/unit.do">
            <input type="hidden" name="method" value="addUnit">
            <!--div的class 为error是验证错误，ok是验证成功-->
            <div>
                <label for="unitCode">单位编码：</label>
                <input type="text" name="unitCode" id="unitCode" value="">
                <!-- 放置提示信息 -->
                <font color="red"></font>
            </div>
            <div>
                <label for="unitName">单位名称：</label>
                <input type="text" name="unitName" id="unitName" value="">
                <font color="red"></font>
            </div>

            <div>
                <label for="unitAddress">单位地址：</label>
                <input type="text" name="unitAddress" id="unitAddress" value="">
                <font color="red"></font>
            </div>


            <div>
                <label for="unitPhone">单位电话：</label>
                <input type="text" name="unitPhone" id="unitPhone" value="">
                <font color="red"></font>
            </div>

            <div>
                <label for="unitFax">单位邮件：</label>
                <input type="text" name="unitFax" id="unitFax" value="">
                <font color="red"></font>
            </div>

            <div>
                <label >部门角色：</label>
                <!-- 列出所有的单位角色分类--来自哪个部门，userDepartmentId是部门表里面的部门号对应的id -->
                <select name="DepartmentId" id="DepartmentId"></select>
            </div>


            <div class="providerAddBtn">
                <input type="submit" name="addUnit" id="addUnit" value="保存" >
                <input type="cancel" id="back" name="back" value="返回" >
            </div>

        </form>
    </div>
</div>
</section>
<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/unitadd.js"></script>
