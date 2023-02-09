<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: Vova
  Date: 08.02.2023
  Time: 10:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>

</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<ul style="font-size: large">
    <li><a href="users">add Meal</a></li>
</ul>
<table cellspacing="2" border="1" cellpadding="5" width="600">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
<%
    List<MealTo> allList= (List<MealTo>) request.getAttribute("allMealTo");
    for(MealTo xxx:allList){
        String colText="";
        if(xxx.isExcess()){
            colText="#ff0000";
        }else colText="#00ff00";
        %>
    <tr>
        <td><font color=<%= colText%>> <%= xxx.getDateTime().toString().replaceAll("T"," ")%></font></td>
        <td> <font color=<%= colText%>> <%= xxx.getDescription()%></font></td>
        <td><font color=<%= colText%>> <%= xxx.getCalories()%></font></td>
        <td> <%= "Update"%></td>
        <td> <%= "Delete"%></td>
    </tr

        <%
    }
%>
</table>
</body>
</html>
