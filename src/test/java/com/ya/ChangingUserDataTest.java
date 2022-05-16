package com.ya;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;


public class ChangingUserDataTest {

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
    @DisplayName("Authorized user change his data")
    @Description("Authorized user try to change name field")
    public void changeNameField() {
        user.signUp();
        user.setName("qew");
        user.changingDataOfAuthUser().then().statusCode(200).and().body("user.name", equalTo(user.getName()));

    }

    @Test
    @DisplayName("Authorized user change his data")
    @Description("Authorized user try to change email field")
    public void changeEmailField() {
        user.signUp();
        user.setEmail("qe@sd.ru");
        user.changingDataOfAuthUser().then().statusCode(200).and().body("user.email", equalTo(user.getEmail()));
    }

    @Test
    @DisplayName("Authorized user change his data")
    @Description("Authorized user try to change password field")
    public void changePasswordField() {
        user.signUp();
        user.setEmail("qweqweqwe@sdff.ru");
        user.changingDataOfAuthUser().then().statusCode(200).and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Unauthorized user change his data")
    @Description("Unauthorized user try to change email field")
    public void unauthorizedUserChangeEmailField() {
        user.setEmail("qweqweqw@dfs.ru");
        user.changingDataOfAuthUser().then().statusCode(401).body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Unauthorized user change his data")
    @Description("Unauthorized user try to change password field")
    public void unauthorizedUserChangePasswordField() {
        user.setEmail("123123123");
        user.changingDataOfAuthUser().then().statusCode(401).body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Unauthorized user change his data")
    @Description("Unauthorized user try to change name field")
    public void unauthorizedUserChangeNameField() {
        user.setEmail("Sdsfsdg");
        user.changingDataOfAuthUser().then().statusCode(401).body("message", equalTo("You should be authorised"));
    }
}


