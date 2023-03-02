package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private MealService service;


    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id,authUserId() );
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id,authUserId());
    }

    public List<MealTo> getAll() {
        log.info("getAll not time limit");
        return MealsUtil.getTos(service.getAll(authUserId()),authUserCaloriesPerDay());
    }

    public List<MealTo> getAll(LocalDate startDate, LocalTime startTime,LocalDate endDate, LocalTime endTime) {
        log.info("getAll filter time");
        return MealsUtil.getFilteredTos(service.getAll(authUserId()),authUserCaloriesPerDay(),startDate,startTime,endDate,endTime);
    }

    public void save(Meal meal) {
        log.info("save");
        service.save(meal,authUserId());
    }
}