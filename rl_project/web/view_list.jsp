<%-- 
    Document   : question_list
    Created on : Jul 9, 2018, 2:50:46 PM
    Author     : d.gorshenin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url= "/includes/newheader.jsp" />
<c:import url= "/includes/icons.jsp" />
        <h3>(всего ${total} элементов)</h3>
        
        ${list_table}
<c:import url= "/includes/newfooter.jsp" />
