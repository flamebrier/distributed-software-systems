<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Все автомобили</title>
    <style><%@include file="/styles/style.jsp"%></style>
</head>
<body>
<header>
    <h1>CarShop</h1>
    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/newCar">Добавить автомобиль</a></li>
            <li><a href="${pageContext.request.contextPath}/list">Список автомобилей</a></li>
        </ul>
    </nav>
    <table border="1">
        <caption><h2>Список автомобилей</h2></caption>
        <tr>
            <th>ID</th>
            <th>Марка</th>
            <th>Модель</th>
            <th>Цвет</th>
            <th>Год выпуска</th>
            <th>Пробег</th>
            <th>ID владельца</th>
            <th>Цена</th>
            <th>Действия</th>
        </tr>
        <c:set var="num" scope="page" value="1"/>
        <c:forEach var="car" items="${cars}">
            <tr>
                <td><c:out value="${num} (${car.id})" /></td>
                <td><c:out value="${car.brand}" /></td>
                <td><c:out value="${car.model}" /></td>
                <td><c:out value="${car.color}" /></td>
                <td><c:out value="${car.year}" /></td>
                <td><c:out value="${car.mileage}" /></td>
                <td><c:out value="${car.owner}" /></td>
                <td><c:out value="${car.price}" /></td>
                <td>
                    <a href="${pageContext.request.contextPath}/editCar?id=<c:out value='${car.id}' />">Редактировать</a>
                    <a href="${pageContext.request.contextPath}/deleteCar?id=<c:out value='${car.id}' />">Удалить</a>
                </td>
            </tr>
            <c:set var="num" value="${num + 1}"/>
        </c:forEach>
        <c:remove var="num" scope="page" />
    </table>
</header>
</body>
</html>
