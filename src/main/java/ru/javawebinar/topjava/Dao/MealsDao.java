package ru.javawebinar.topjava.Dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealsDao {

    private Connection connection;

    public MealsDao() {
        connection = DbUtil.getConnection();
    }

    public void addMeal(Meal meal) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into meals(dateTime,description,calories) values (?, ?, ? )");
            // Parameters start with 1
            creaitMeal(meal, preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMeal(int userId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from meals where id=?");
            // Parameters start with 1
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMeal(Meal meal) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update meals set dateTime=?, description=?, calories=? " +
                            "where id=?");
            // Parameters start with 1
            creaitMeal(meal, preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void creaitMeal(Meal meal, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setTimestamp(1, new Timestamp(meal.getDateTime().getYear()-1900,meal.getDateTime().getMonthValue()-1,meal.getDateTime().getDayOfMonth(),meal.getDateTime().getHour(),meal.getDateTime().getMinute(),meal.getDateTime().getSecond(),0) );
        preparedStatement.setString(2, meal.getDescription());
        preparedStatement.setInt(3, meal.getCalories());
        if(meal.getId()>0){
            preparedStatement.setInt(4, meal.getId());
        }
        preparedStatement.executeUpdate();
    }

    public List<Meal> getAllMeals() {
        List<Meal> users = new ArrayList<Meal>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from meals");
            while (rs.next()) {
                Meal meal = new Meal();
                meal.setId(rs.getInt("id"));
                meal.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
                meal.setDescription(rs.getString("description"));
                meal.setCalories(rs.getInt("calories"));
                users.add(meal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public Meal getUserById(int userId) {
        Meal meal = new Meal();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from meals where id=?");
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                meal.setId(rs.getInt("id"));
                meal.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
                meal.setDescription(rs.getString("description"));
                meal.setCalories(rs.getInt("calories"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meal;
    }
}
