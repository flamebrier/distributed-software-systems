<%--
  Created by IntelliJ IDEA.
  User: Brier
  Date: 06.11.2019
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Все автомобили</title>
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
      <c:forEach var="car" items="${cars}">
        <tr>
          <td><c:out value="(${car.id})" /></td>
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
      </c:forEach>
    </table>
      </header>
  </body>
</html>
