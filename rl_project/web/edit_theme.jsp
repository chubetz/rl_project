<%-- 
    Document   : edit_theme
    Created on : Jun 29, 2018, 11:18:33 PM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url= "/includes/newheader.jsp" />
<%--c:import url= "/includes/icons.jsp" /--%>
        <p>${theme.getId() < 0  ? "<Новая тема>" : theme}</p>
        <form name="${action}" action="controller" method="POST">
            <input type="hidden" name="action" value="${action}">        
            <input type="hidden" name="tid" value="${theme.getId()}">        
            <label>Номер</label>
            <input type="text" name="number" value="${theme.getDbl("number")}">
            <br>
            <label>Название</label>
            <input type="text" name="text" value="${theme.getStr("text")}">
            <br>
            <label>Область</label>
            ${theme.getRealmsHTML()}
            <br>
            <input type="submit" value="Отправить" /> 
        </form>
<c:import url= "/includes/newfooter.jsp" />
