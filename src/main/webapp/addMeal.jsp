<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%--
  Created by IntelliJ IDEA.
  User: Vova
  Date: 11.02.2023
  Time: 9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>addMeal</title>
    <title></title>
</head>

<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit Meal</h2>
<%
   Meal meal= (Meal) request.getAttribute("meal");
   String desk="";
   String cal="";

   if(meal!=null){
       desk= meal.getDescription();
       cal+=meal.getCalories();
   }
%>
<form action="meals" name="frmAddUser" method="post">
    <%
        if(meal!=null){
    %>
    <input type="hidden" name="idMeal" value=<%= meal.getId()%>>
    <%
        }
    %>
    <p>
        <label for="localdate">DateTime: </label>
        <input type="datetime-local" id="localdate" name="date"/>
    </p>
    <p>
        <label for="description">Description: </label>
        <input type="text"  id="description" name="description" placeholder=<%= desk%>/>
    </p>
    <p>
        <label for="calories">Calories: </label>
        <input type="text" id="calories" name="calories" placeholder=<%= cal%>/>
    </p>
    <p>
        <input type="submit" value="Save"/>
        <button onclick="window.history.back()" type="button">Cancel</button>

    </p>
</form>
</body>
</html>

