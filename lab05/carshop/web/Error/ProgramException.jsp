<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>CarShop</title>
    <style>
        html {
            font-family: 'Trebuchet MS', 'Arial',serif;
        }
        h1 {
            font-size: 20vmin;
        }
        h3 {
            font-size: 2vmin;
        }
        h2 {
            font-size: 3vmin;
        }
    </style>
</head>
<body>
<div style="text-align: center;">
    <header>
        <h2>${pageContext.errorData.statusCode}</h2>
        <h3>${pageContext.exception}</h3>
        <h1>Ошибка</h1>
    </header>
</div>
</body>
</html>
