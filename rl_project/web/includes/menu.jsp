<%-- 
    Document   : menu
    Created on : Nov 25, 2018, 3:47:34 PM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--table border="0">
    <tr>
        <td background="${pageContext.request.contextPath}/images/fon1.png">
            О нас
        </td>
    </tr>
</table--%>
<a href="index.jsp">
<div style="display:inline-block; position:relative;">
    <img src="${pageContext.request.contextPath}/images/menu_blue.png">
    <span style="display:inline-block; position:absolute; top:5px; left:5px;">Главная страница</span>
</div>
</a>
<a href="learning.jsp">
<div style="display:inline-block; position:relative;">
    <img src="${pageContext.request.contextPath}/images/menu_green.png">
    <span style="display:inline-block; position:absolute; top:5px; left:5px;">Модуль обучения</span>
</div>
</a>
<a href="">
<div style="display:inline-block; position:relative;">
    <img src="${pageContext.request.contextPath}/images/menu_orange.png">
    <span style="display:inline-block; position:absolute; top:5px; left:5px;">Модуль проверки</span>
</div>
</a>
<a href="">
<div style="display:inline-block; position:relative;">
    <img src="${pageContext.request.contextPath}/images/menu_rosa.png">
    <span style="display:inline-block; position:absolute; top:5px; left:5px;">О нас</span>
</div>
</a>

