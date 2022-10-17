<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%--UTF-8-Bom--%>
<%@include file="/jsp/common/head.jsp"%>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>单位管理页面</span>
    </div>
    <div class="search">
        <form method="get" action="${pageContext.request.contextPath }/jsp/unit.do">
            <input name="method" value="query" class="input-text" type="hidden">

            <span>所属单位：</span>
            <select name="queryUserRole">
                <c:if test="${roleList != null }">
                    <option value="0">--请选择--</option>
                    <%--管理员还是普通用户--%>
                    <c:forEach var="role" items="${roleList}">
                        <option <c:if test="${role.unitCode == queryUserRole }">selected="selected"</c:if>
                                value="${role.unitCode}">${role.unitName}</option>
                    </c:forEach>
                </c:if>
            </select>

            <span>所属部门：</span>
            <select name="queryUserRole">
                <c:if test="${roleList != null }">
                    <option value="0">--请选择--</option>
                    <%--管理员还是普通用户--%>
                    <c:forEach var="role" items="${roleList}">
                        <option <c:if test="${role.unitCode == queryUserRole }">selected="selected"</c:if>
                                value="${role.unitCode}">${role.unitName}</option>
                    </c:forEach>
                </c:if>
            </select>

            <input type="hidden" name="pageIndex" value="1"/>
            <input	value="查 询" type="submit" id="searchbutton">
            <a href="${pageContext.request.contextPath}/jsp/useradd.jsp" >添加用户</a>
        </form>
    </div>

    <!--用户展示-->
    <table class="providerTable" cellpadding="0" cellspacing="0">
        <tr class="firstTr">
            <th width="10%">用户角色</th>
            <th width="20%">用户名称</th>
            <th width="10%">所属单位</th>
            <th width="10%">所属部门</th>
            <th width="30%">操作</th>
        </tr>
        <c:forEach var="user" items="${userList }" varStatus="status">
            <tr>
                <td>
                    <span>${user.userCode }</span>
                </td>
                <td>
                    <span>${user.userName }</span>
                </td>
                <td><span>${user.unitName }</span></td>
                <td><span>${user.department }</span></td>

                <td>
                    <span><a class="viewUser" href="javascript:;" userid=${user.id} username=${user.userName }><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
                    <span><a class="modifyUser" href="javascript:;" userid=${user.id} username=${user.userName }><img src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
                    <span><a class="deleteUser" href="javascript:;" userid=${user.id} username=${user.userName }><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
                </td>
            </tr>
        </c:forEach>
    </table>

    <input type="hidden" id="totalPageCount" value="${totalPageCount}"/>
    <c:import url="rollpage.jsp">
        <c:param name="totalCount" value="${totalCount}"/>
        <c:param name="currentPageNo" value="${currentPageNo}"/>
        <c:param name="totalPageCount" value="${totalPageCount}"/>
    </c:import>
</div>
</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeUse">
    <div class="removerChid">
        <h2>Reminder</h2>
        <div class="removeMain">
            <p >Are you sure you want to delete this user?</p>
            <%--  <a href="#" id="yes">Sure</a>--%>
            <a href="#" id ="yes" onclick="location.replace('unitlist.jsp') ">sure</a>
            <a href="#" id="no">Cancel</a>
        </div>
    </div>
</div>

<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/unitlist.js"></script>
