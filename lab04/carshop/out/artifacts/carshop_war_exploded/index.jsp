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
    <title>CarShop</title>
      <style><%@include file="styles/style.css"%></style>
  </head>
  <body>
  <div style="text-align: center;">
    <header>
    <h1>CarShop</h1>
      <nav>
        <ul>
          <li><a href="${pageContext.request.contextPath}/newCar">Добавить автомобиль</a>
          <li><a href="${pageContext.request.contextPath}/list">Список автомобилей</a>
        </ul>
      </nav>
    </header>
  </div>
  </body>
</html>
