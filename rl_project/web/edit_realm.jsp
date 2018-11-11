<%-- 
    Document   : edit_realm
    Created on : Jun 29, 2018, 10:57:49 AM
    Author     : d.gorshenin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url= "/includes/newheader.jsp" />
<%--c:import url= "/includes/icons.jsp" /--%>
        <p>${realm.getId() < 0  ? "<Новая область>" : realm}</p>
        <form name="${action}" action="controller" method="POST">
            <input type="hidden" name="action" value="${action}">        
            <input type="hidden" name="rid" value="${realm.getId()}">        
            <label>Код</label>
            <input type="text" name="text" value="${realm.getStr("text")}">
            <br>
            <label>Описание</label>
            <input type="text" name="description" value="${realm.getStr("description")}">
            <br>
            <input type="submit" value="Отправить" /> 
        </form>
<c:import url= "/includes/newfooter.jsp" />
