<%-- 
    Document   : index
    Created on : Sep 8, 2018, 6:36:02 PM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="ru.rl.project.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setAttribute("title", Constants.EDIT_TITLE);
%>
<c:import url= "/includes/newheader.jsp" />
<c:import url= "/includes/icons.jsp" />

        <table border="0">
            <tr>
                <%--td>
                    <c:import url= "/widgets/statistics.jsp" />
                </td--%>
                <td valign="top">
                    <c:import url= "/widgets/data_management.jsp" />
                </td>
            </tr>
        </table>
            
<c:import url= "/includes/newfooter.jsp" />
