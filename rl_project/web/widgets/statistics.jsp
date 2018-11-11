<%-- 
    Document   : statistics
    Created on : Sep 4, 2018, 6:15:44 AM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.rl.project.users.*" %>
<%
    IStatistics statistics = User.getDefaultUser().getStatistics();
    pageContext.setAttribute("stats", statistics);
%>
<table cellpadding="10" class="bg1">
    <tr>
        <td>
<table bgcolor ="white" border="1" cellpadding="5">
    <tr>
        <td class="min_header" bgcolor="#d8cFaA">Статистика</td>
    </tr>
    <tr>
        <td class="stat1" bgcolor="#C0EFF9">
            Предметных областей: <b>${stats.realmsQty}</b>
        </td>
    </tr>
    <tr>
        <td class="stat1" bgcolor="#B4ECB7">
            Тем: <b>${stats.themesQty}</b>
        </td>
    </tr>
    <tr>
        <td class="stat1" bgcolor="#EDF59E">
            Карточек: <b>${stats.questionsQty}</b>
        </td>
    </tr>
    <tr>
        <td class="stat1" align="right" background="images/fon_nota_bene.jpg">
            <span class="small_border" >&nbsp;Nota bene: <b>${stats.questionsNotaBeneQty}</b>&nbsp;</span>
        </td>
    </tr>
    <tr>
        <td class="stat1" align="right" background="images/fon_common.jpg">
            <span class="small_border" >&nbsp;Общих: <b>${stats.questionsCommonQty}</b>&nbsp;</span>
        </td>
    </tr>
    <tr>
        <td class="stat1" align="right" background="images/fon_test.jpg">
            <span class="small_border" >&nbsp;Тестовых: <b>${stats.questionsTestQty}</b>&nbsp;</span>
        </td>
    </tr>
    <tr>
        <td bgcolor="#D3D3D3" class="tah">
            <c:if test="${stats.examsQty != 0}">
                <i>Знания проверены <b>${stats.examsQty}</b> раз со средней оценкой <b>${stats.averageScore}</b></i>
            </c:if>
        </td>
    </tr>

</table>
            
        </td>
    </tr>
</table>

