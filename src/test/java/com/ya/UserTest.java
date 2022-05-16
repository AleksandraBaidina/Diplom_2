package com.ya;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserTest {

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
    @DisplayName("Create new user")
    @Description("Create new user and try to verify if he is really has created")
    public void createUniqueUser() {
        user.signUp().then().statusCode(200).body("success",equalTo(true));

    }

    @Test
    @DisplayName("Create user who is already register")
    @Description("Create user who is already register")
    public void createRegisteredUser() {
        user.signUp();
        user.signUp().then().statusCode(403).and().body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Create user with empty name field")
    @Description("Try to create user with empty name field")
    public void createUserWithEmptyNameField() {
        user.setName("");
        user.signUp().then().statusCode(403).and().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Create user with empty email field")
    @Description("Try to create user with empty email field")
    public void createUserWithEmptyEmailField() {
        user.setEmail("");
        user.signUp().then().statusCode(403).and().body("message", equalTo("Email, password and name are required fields"));

    }

    @Test
    @DisplayName("Create user with empty password field")
    @Description("Try to create user with empty password field")
    public void createUserWithEmptyPasswordField() {
        user.setPassword("");
        user.signUp().then().statusCode(403).and().body("message", equalTo("Email, password and name are required fields"));

    }

    @Test
    @DisplayName("Create user without password field")
    @Description("Try to create user without password field")
    public void createUserWithoutPasswordField() {
        JSONObject reqBoby = new JSONObject();
        reqBoby.put("email", RandomStringUtils.randomAlphabetic(6) + '@' + RandomStringUtils.randomAlphabetic(4) + ".com");
        reqBoby.put("name", RandomStringUtils.randomAlphabetic(7));
        user.signUp(reqBoby.toString()).then().statusCode(403).and().body("message", equalTo("Email, password and name are required fields"));
    }

}
