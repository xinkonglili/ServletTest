<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>单位管理页面 >> 单位修改页面</span>
    </div>
    <div class="providerAdd">
        <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath }/jsp/unit.do">
            <input type="hidden" name="method" value="unitmodifyexe">
            <input type="hidden" name="unitid" value="${unit.unitid}"/>
            <div>
                <label for="unitName">单位名称：</label>
                <input type="text" name="unitName" id="unitName" value="${ResultUnit.unitName }">
                <font color="red"></font>
            </div>
            <div>
                <label for="unitCode">单位编号：</label>
                <input type="text" name="unitCode" id="unitCode" value="${ResultUnit.unitCode }">
                <font color="red"></font>
            </div>

            <div>
                <label >公司所属部门修改：</label>
                <!-- 列出所有的部门角色分类 -->
                <input type="hidden" value="${ResultUnit.userDepartmentId }" id="rid1" />
                <select name="userDepartmentId" id="userDepartmentId"></select>
                <font color="red"></font>
            </div>


            <div class="providerAddBtn">
                <input type="button" name="save" id="save" value="保存" />
                <input type="button" id="back" name="back" value="返回"/>
            </div>
        </form>
    </div>
</div>
</section>
<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/unitmodify.js"></script>
