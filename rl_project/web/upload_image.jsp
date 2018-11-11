<%-- 
    Document   : upload_image
    Created on : Jul 13, 2018, 10:44:10 PM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Загрузка изображения</title>
    </head>
    <body>
<%@ include file="header.jsp" %>
        <h3>Загрузка изображения</h3>
        <form name="upload" action="controller" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="action" value="upload_image">        
            <input type="file" name="file" accept="image/jpeg,image/png,image/gif">
            <input type="submit" value="Загрузить" /> 
        </form>
    </body>
</html>
