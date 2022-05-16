package com.ya;

import com.ya.Order;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderTest {

    User user;

    @Before
    public void setUp() {
        user = User.getRandom();
    }

    @After
    public void tearDown(){
        user.deleteUser();
    }

    @Test
    @DisplayName("Authorized user create order")
    @Description("Authorized user create order")
    public void createAuthorizedUserOrderWithIngredients() {
        user.login();
        Order order = new Order();
        order.addIngredients("61c0c5a71d1f82001bdaaa6d");
        user.makeOrder(order).then().statusCode(200).and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Authorized user create order with invalid hash")
    @Description("Authorized user try to create order with invalid hash")
    public void createAuthorizedUserOrderWithInvalidHash() {
        user.login();
        Order order = new Order();
        order.addIngredients("61c0c5a71d1f82001bdaaa6d111");
        user.makeOrder(order).then().statusCode(500);
    }

    @Test
    @DisplayName("Authorized user create order without ingredients")
    @Description("Authorized user try to create order without ingredients")
    public void createAuthorizedUserOrderWithoutIngredients() {
        user.login();
        Order order = new Order();
        user.makeOrder(order).then().statusCode(400);
    }


    @Test//I assumed that expected result should have 401 because user cannot create order for non authorized user
    @DisplayName("Unauthorized user create order")
    @Description("Unauthorized user try to create order")
    public void createUnauthorizedUserOrderWithoutAuthorization() {
        Order order = new Order();
        order.addIngredients("61c0c5a71d1f82001bdaaa6d");
        user.makeOrder(order).then().statusCode(401);
    }

    @Test//I assumed that expected result should have 401 because user cannot create order for non authorized user
    @DisplayName("Unauthorized user create order with invalid hash")
    @Description("Unauthorized user try to create order with invalid hash")
    public void createUnauthorizedUserOrderWithInvalidHash() {
        Order order = new Order();
        order.addIngredients("61c0c5a71d1f82001bdaaa6d11111");
        user.makeOrder(order).then().statusCode(401);
    }

    @Test//I assumed that expected result should have 401 because user cannot create order for non authorized user
    @DisplayName("Unauthorized user create order without ingredients")
    @Description("Unauthorized user try to create order without ingredients")
    public void createUnauthorizedUserOrderWithoutIngredients() {
        Order order = new Order();
        user.makeOrder(order).then().statusCode(401);
    }


    @Test
    @DisplayName("Authorized user get his orders")
    @Description("Authorized user try to get his orders")
    public void getOrdersCurrentAuthorizedUser() {
        user.signUp();
        user.login();
        Order order = new Order();
        order.addIngredients("61c0c5a71d1f82001bdaaa6d");
        order.addIngredients("61c0c5a71d1f82001bdaaa71");
        user.makeOrder(order);
        user.getOrderCurrentUser().then().statusCode(200).body("success",equalTo(true));

    }

    @Test
    @DisplayName("Unauthorized user get his orders")
    @Description("Unauthorized user try to get his orders")
    public void getOrderCurrentUnauthorizedUser() {
        Order order = new Order();
        order.addIngredients("61c0c5a71d1f82001bdaaa6d");
        order.addIngredients("61c0c5a71d1f82001bdaaa71");
        user.makeOrder(order);
        user.getOrderCurrentUser().then().statusCode(401).and().body("message", equalTo("You should be authorised"));
    }

}






