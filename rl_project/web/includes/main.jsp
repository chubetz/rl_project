<%-- 
    Document   : newheader
    Created on : Sep 10, 2018, 6:19:40 AM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table border="1" width="100%">
    <tr>
        <td align="center" colspan="2">
            ${subtitle}
        </td>
    </tr>
    <tr>
        <td width="30%" valign="top">
            <c:import url= "/includes/menu.jsp" />
        </td>
        <td width="70%"rowspan="2">
            <c:import url= "${jsp_main}" />
        </td>
    </tr>
    <tr>
        <td width="30%">
            ${tree}
        </td>
        
    </tr>
    
</table>
