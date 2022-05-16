package com.ya;

import io.qameta.allure.Step;
import org.json.JSONObject;

import java.util.ArrayList;

public class Order {
    public ArrayList<String> ingredients = new ArrayList<>();

    @Step("Add ingredients to order")
    public void addIngredients(String id) {//добавл ингредиенты в массив ingredients
        ingredients.add(id);
    }

    public String toJSON() {
        JSONObject reqBoby = new JSONObject();
        reqBoby.put("ingredients", ingredients);
        return reqBoby.toString();

    }
}
