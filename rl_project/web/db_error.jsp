<%-- 
    Document   : db_error
    Created on : Jun 16, 2018, 12:08:26 PM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ошибка Базы Данных</title>
    </head>
    <body>
        <h1>Ошибка Базы Данных</h1>
        <p>
        ${exception}
        <p>
            <b>${exception.getRequest()}</b>
        <p>
        ${exception.getExceptionInfoHTML()}
    </body>
</html>
