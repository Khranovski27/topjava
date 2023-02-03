package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> allDayCalories = new TreeMap<>();
        Set<UserMeal> userMeals = new TreeSet<>((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        for (UserMeal xxx : meals) {
            int time = xxx.getDateTime().toLocalTime().toSecondOfDay();
            LocalDate date = xxx.getDateTime().toLocalDate();
            allDayCalories.merge(date, xxx.getCalories(), Integer::sum);
            if (time >= startTime.toSecondOfDay() && time < endTime.toSecondOfDay()) {
                userMeals.add(xxx);
            }
        }
        List<UserMealWithExcess> otv = new ArrayList<>();
        for (UserMeal xxx : userMeals) {
            boolean kal = allDayCalories.get(xxx.getDateTime().toLocalDate()) > caloriesPerDay;
            otv.add(new UserMealWithExcess(xxx.getDateTime(), xxx.getDescription(), xxx.getCalories(), kal));
        }
        return otv;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> allDayCalories = meals.stream()
                .collect(Collectors.groupingBy(UserMael -> UserMael.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
        return meals.stream()
                .filter(x -> x.getDateTime().toLocalTime().toSecondOfDay() >= startTime.toSecondOfDay() && x.getDateTime().toLocalTime().toSecondOfDay() < endTime.toSecondOfDay())
                .sorted((x1, x2) -> x2.getDateTime().compareTo(x1.getDateTime()))
                .map(x -> new UserMealWithExcess(x.getDateTime(), x.getDescription(), x.getCalories(), caloriesPerDay < allDayCalories.get(x.getDateTime().toLocalDate())))
                .collect(Collectors.toList());
    }

//    public static HashMap<LocalDate,HashMap<UserMeal,Integer>> filteredByStreams2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
//        return meals.stream()
//                .map(x ->new HashMap<LocalDate,HashMap<UserMeal,Integer>>())
//                .collect(Collectors.
////        Map<LocalDate, Integer> allDayCalories = meals.stream()
////                .collect(Collectors.groupingBy(UserMael -> UserMael.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
////        return meals.stream()
////                .filter(x -> x.getDateTime().toLocalTime().toSecondOfDay() >= startTime.toSecondOfDay() && x.getDateTime().toLocalTime().toSecondOfDay() < endTime.toSecondOfDay())
////                .sorted((x1, x2) -> x2.getDateTime().compareTo(x1.getDateTime()))
////                .map(x -> new UserMealWithExcess(x.getDateTime(), x.getDescription(), x.getCalories(), caloriesPerDay < allDayCalories.get(x.getDateTime().toLocalDate())))
////                .collect(Collectors.toList());
//    }
    }
