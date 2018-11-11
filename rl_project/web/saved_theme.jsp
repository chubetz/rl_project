<%-- 
    Document   : saved_theme
    Created on : Jun 29, 2018, 11:28:19 PM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url= "/includes/newheader.jsp" />
<c:import url= "/includes/icons.jsp" />
        <table>
            <tr>
                <td>
                    <form name="back_to_edit" action="controller" method="POST">
                        <input type="hidden" name="action" value="edit_theme">        
                        <input type="hidden" name="id" value="${theme.getId()}">        
                        <input type="submit" value="Вернуться к редактированию" /> 
                    </form>                    
                </td>
                <td>
                    <form name="back_to_list" action="view" method="GET">
                        <input type="hidden" name="info" value="themes">        
                        <input type="submit" value="Переход к списку тем" /> 
                    </form>                    
                </td>
            </tr>
        </table>
        <h2>Тема сохранена</h2>
        ${theme}<br>
        <br>
        Номер: <b>${theme.getDbl("number")}</b>  Название: <b>${theme.getStr("text")}</b>
<c:import url= "/includes/newfooter.jsp" />
