<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>单位管理页面 >> 单位信息查看页面</span>
    </div>
    <div class="providerView">
        <p><strong>单位编码：</strong><span class="1">${unit.unitCode }</span></p>
        <p><strong>单位名称：</strong><span class="1">${unit.unitName }</span></p>
        <p><strong>单位地址：</strong><span class="1">${unit.address }</span></p>
        <p><strong>单位电话：</strong><span class="1">${unit.phone}</span></p>
        <p><strong>单位邮件：</strong><span class="1">${unit.fax }</span></p>
        <p><strong>部门角色：</strong><span class="1">${unit.departmentName}</span></p>
        <div class="providerAddBtn">
            <input type="button" id="back" name="back" value="返回" >
        </div>
    </div>
</div>
</section>
<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/unitview.js"></script>