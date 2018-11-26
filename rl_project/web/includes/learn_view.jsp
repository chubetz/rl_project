<%-- 
    Document   : profile_learning
    Created on : Nov 25, 2018, 11:15:52 PM
    Author     : mithia
--%>

<%@page import="ru.rl.project.edu.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%



%>
<table border="0"  width="100%">
    <tr>
        <td width="100%" align="center" valign="middle">
            
            <table border="1" cellpadding="5" width="100%" bgcolor="#FCF5C0">
                <tr>
                    <td bgcolor="${learnElement.titleBgcolor}">
                        ${learnElement.title}
                    </td>
                </tr>
                <c:if test="${learnElement.subtitle != null}">
                <tr>
                    <td bgcolor="${learnElement.subtitleBgcolor}">
                        ${learnElement.subtitle}
                    </td>
                </tr>
                    
                </c:if>
                <tr>
                    <td>
                        ${learnElement.form}
                    </td>
                </tr>
                
            </table>
            
            
        </td>
    </tr>
</table>