<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><decorator:title/></title>
    <style><%@include file="styles/style.jsp"%></style>
</head>
<body>
<div style="text-align: center;">
    <header>
        <h1>CarShop</h1>
        <nav>
            <ul>
                <li><a href="${pageContext.request.contextPath}/webapp/newCar">Добавить автомобиль</a>
                <li><a href="${pageContext.request.contextPath}/webapp/list">Список автомобилей</a>
            </ul>
        </nav>
    </header>
</div>
</body>
</html>
