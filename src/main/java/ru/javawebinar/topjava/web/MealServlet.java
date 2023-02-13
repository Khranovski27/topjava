package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.Dao.MealsDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final MealsDao mealsDao= new MealsDao();
    private static final int CALORIES_PER_DAY =2000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String otv=request.getParameter("action");
        log.debug("redirect to meals"+otv);
        if(otv!=null){
            if (otv.equalsIgnoreCase("delete")) {
                mealsDao.deleteMeal(Integer.parseInt(request.getParameter("mealId")));
            } else if (otv.equalsIgnoreCase("edit")) {
                request.setAttribute("meal", mealsDao.getUserById(Integer.parseInt(request.getParameter("mealId"))));
                request.getRequestDispatcher("addMeal.jsp").forward(request, response);
            }
        }
        List<Meal>meals= mealsDao.getAllMeals();
        List<MealTo> allMael = MealsUtil.filteredByStreams(meals, null, null, CALORIES_PER_DAY);
        request.setAttribute("allMealTo", allMael);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
 //       response.sendRedirect("users.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String da=request.getParameter("description");
        log.debug("redirect to meals post metod "+da);
        Meal meal = new Meal();
        meal.setDescription(request.getParameter("description"));
            meal.setCalories(Integer.parseInt(request.getParameter("calories")));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime time = LocalDateTime.parse(request.getParameter("date"),formatter);
            meal.setDateTime(time);

        String id = request.getParameter("idMeal");
        if(id == null || id.isEmpty())
        {
            mealsDao.addMeal(meal);
        }
        else
        {
            meal.setId(Integer.parseInt(id));
            mealsDao.updateMeal(meal);
        }
        response.sendRedirect("meals.jsp");
        //response.sendRedirect("index.html");
    }
}
