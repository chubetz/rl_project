<%-- 
    Document   : examFinished
    Created on : Jul 20, 2018, 12:38:44 AM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url= "/includes/newheader.jsp" />
<c:import url= "/includes/icons.jsp" />
        <h3>${examResult}</h3>
        Открыть профиль темы: ${theme.getProfileLink(theme.title)} 
<c:import url= "/includes/newfooter.jsp" />
