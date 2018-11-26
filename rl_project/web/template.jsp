<%-- 
    Document   : learn_view
    Created on : Nov 26, 2018, 4:46:59 PM
    Author     : d.gorshenin
--%>

<%@page import="ru.rl.project.util.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    //request.setAttribute("jsp_main", "/includes/empty.jsp");
    request.setAttribute("title", Constants.MAIN_TITLE);
    //request.setAttribute("subtitle", Constants.LEARNING_MODULE);
    
    
    
%>
<c:import url= "/includes/newheader.jsp" />

<c:import url= "/includes/main.jsp" />

<c:import url= "/includes/newfooter.jsp" />
