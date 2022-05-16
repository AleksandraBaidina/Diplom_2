package com.ya;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest {

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
    @DisplayName("Login existing user")
    @Description("Existing user try to login")
    public void loginWithExistingCredentials() {
        user.signUp();
        user.login().then().assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test //не проходит тк при смене пароля не дается токен, а я его беру в loginUser()
    @DisplayName("Login existing user with wrong email")
    @Description("Existing user try to login with wrong email")
    public void loginWithWrongEmail() {
        user.signUp();
        user.setEmail("sdf@mail.ru");
        user.login().then().statusCode(401).and().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Login existing user with wrong password")
    @Description("Existing user try to login with wrong password")
    public void loginWithWrongPassword() {
        user.signUp();
        user.setPassword("sdf123");
        user.login().then().statusCode(401).and().body("message", equalTo("email or password are incorrect"));

    }

    @Test
    @DisplayName("Login existing user with wrong password")
    @Description("Existing user try to login with wrong password")
    public void loginUserWithoutEmailField() {
        JSONObject reqBody = new JSONObject();
        reqBody.put("password", RandomStringUtils.randomAlphabetic(10));
        reqBody.put("name", RandomStringUtils.randomAlphabetic(7));
        user.login(reqBody.toString()).then().statusCode(401).and().body("message", equalTo("email or password are incorrect"));
    }

}
