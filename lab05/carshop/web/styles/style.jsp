<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
header {
    font-family: 'Trebuchet MS', 'Arial', serif;
}
header h1 {
    text-align: center;
}
header h3 {
    text-align: right;
}
nav ul {
    list-style-type: none; /*remove list msarkers*/
    margin: 0; /*remove useless auto margin*/
    padding-left: 30%; /*remove useless auto padding*/
    text-align: center; /*place menu at the center*/
    background-color: #EFEFEF;
    background-image: url('<c:url value="/img/ford.jpg">ford.jpg</c:url>');
    background-size: 30%;
    background-position: center left;
    background-repeat: no-repeat;
    height:50px;
}
nav ul li {
    display: inline-block; /*show in one line*/
    margin: 5px; /*margin between elements*/
    padding: 10px; /*padding inside element*/
    transition: all 0.5s ease-in-out;
    border-bottom: 0 solid red;
}
nav a {
    text-decoration: none; /*remove link underline*/
    font-size: 1.2em;
    color: tomato;
    transition: all 0.5s ease-in-out;
}
nav a:hover {/*link under pointer*/
    color: red;
}
nav ul li:hover { /*element under pointer*/
    border-bottom: 5px solid red;
}
table {
    margin:0 auto;
    border-collapse: collapse;
    border-color: slategray;
}

form table {
    width: 30%;
    min-width: 300px;
}

form table td {
    padding: 5px;
}

form th {
    text-align: right;
    padding-right: 10px;
}

form td input {
    width: 100%;
}