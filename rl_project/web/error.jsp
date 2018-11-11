<%-- 
    Document   : error
    Created on : Jul 28, 2018, 2:08:50 PM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="ru.rl.project.exception.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ошибка 500</title>
    </head>
    <body>
        <h1>Внутренняя ошибка сервера (ошибка 500)</h1>
        <c:if test="${viewer == null}">
                    <%
                        Exception ex = (Exception)request.getAttribute("javax.servlet.error.exception");
                        //ExceptionViewer viewer = new ExceptionViewer((Exception)pageContext.getAttribute("exception"));
                        ExceptionViewer viewer = new ExceptionViewer(ex);
                        pageContext.setAttribute("viewer", viewer);
                    %>
            
        </c:if>
            <%--c:choose>
                <c:when test="${viewer != null}">
            <h3>${viewer.message}</h3>
            ${viewer.stackTrace}
                </c:when>
                <c:otherwise>
                    <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
                    <%
                        ExceptionViewer viewer = new ExceptionViewer((Exception)pageContext.getAttribute("exception"));
                        pageContext.setAttribute("viewer", viewer);
                    %>
                    
            <h3>${viewer.message}</h3>
            ${viewer.stackTrace}
                </c:otherwise>
            </c:choose--%>

        <h3>${viewer.message}</h3>
        ${viewer.stackTrace}
    </body>
</html>
