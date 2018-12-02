<%-- 
    Document   : index
    Created on : Sep 8, 2018, 6:36:02 PM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="ru.rl.project.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setAttribute("jsp_main", "/includes/empty.jsp");

    request.setAttribute("title", Constants.MAIN_TITLE);
    request.setAttribute("subtitle", Constants.MAIN_PAGE);
    
    request.setAttribute("jsp_main", "/includes/about.jsp");
%>
<c:import url= "/includes/newheader.jsp" />
<%--c:import url= "/includes/icons.jsp" /--%>
<c:import url= "/includes/main.jsp" />

<c:import url= "/includes/newfooter.jsp" />
