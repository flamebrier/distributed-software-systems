<%--
  Created by IntelliJ IDEA.
  User: Brier
  Date: 06.11.2019
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Управление автомобилями</title>
    <style><%@include file="styles/style.css"%></style>
</head>
<body>
<header>
    <h1>CarShop</h1>
    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/newCar">Добавить автомобиль</a>
            <li><a href="${pageContext.request.contextPath}/list">Список автомобилей</a>
        </ul>
    </nav>
<c:if test="${car != null}">
<form action="updateCar" method="post">
    </c:if>
    <c:if test="${car == null}">
    <form action="addCar" method="post">
        </c:if>
        <h2>
            <c:if test="${car != null}">
                Редактировать автомобиль
            </c:if>
            <c:if test="${car == null}">
                Добавить автомобиль
            </c:if>
        </h2>
        <c:if test="${car != null}">
            <input type="hidden" name="id" value="<c:out value='${car.id}' />" />
        </c:if>
        <table width="70%" border="0">
            <tbody>
            <tr>
                <th scope="row"><label>Марка</label></th>
                <td><input type="text"  name="brand" value="<c:out value='${car.brand}' />" /></td>
            </tr>
            <tr>
                <th scope="row"><label>Модель</label></th>
                <td><input type="text"  name="model"  value="<c:out value='${car.model}' />" /></td>
            </tr>
            <tr>
                <th scope="row"><label>Цвет</label></th>
                <td><input type="text"  name="color"  value="<c:out value='${car.color}' />" /></td>
            </tr>
            <tr>
                <th scope="row"><label>Год выпуска</label></th>
                <td><input type="number" name="year" value="<c:out value='${car.year}' />" /></td>

            </tr>
            <tr>
                <th scope="row"><label>Пробег</label></th>
                <td><input type="text" name="mileage"  value="<c:out value='${car.mileage}' />" /></td>
            </tr>
            <tr>
                <th scope="row"><label>ID владельца</label></th>
                <td><input type="number"  name="owner"  value="<c:out value='${car.owner}' />" /></td>
            </tr>
            <tr>
                <th scope="row"><label>Цена</label></th>
                <td><input type="number"  name="price" value="<c:out value='${car.price}' />" /></td>
            </tr>
            </tbody>
        </table>
        <p style="text-align:center"><input type="submit" value="Сохранить"></p>
    </form>
</header>
    </body>
</html>
