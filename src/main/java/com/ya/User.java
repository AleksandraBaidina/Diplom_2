package com.ya;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;


public class User extends Specification {

    public String accessToken = "";
    private String email;
    private String password;
    private String name;


    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toJSON() { //переводим поля в JSON(используем для создания нового пользователя)/напр передаем в post тело запроса
        JSONObject reqBody = new JSONObject();
        reqBody.put("email", email);
        reqBody.put("password", password);
        reqBody.put("name", name);
        return reqBody.toString();
    }

    @Step("Create random user")
    public static User getRandom() {
        String email = RandomStringUtils.randomAlphabetic(6) + '@' + RandomStringUtils.randomAlphabetic(4) + ".com";
        String password = RandomStringUtils.randomAlphabetic(10);
        String name = RandomStringUtils.randomAlphabetic(7);


        Allure.addAttachment("email",email);
        Allure.addAttachment("password",password);
        Allure.addAttachment("name",name);
        return new User(email, password, name);

    }

    @Step("Register user without fields")
    public static Response signUp(String body) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(body)
                .when()
                .post("/api/auth/register");
    }

    @Step("Login user without fields")
    public static Response login(String body) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(body)
                .when()
                .post("/api/auth/login");
    }

    @Step("Register user")
    public Response signUp() {
        Response response = given()
                .spec(getBaseSpec())
                .and()
                .body(toJSON())
                .when()
                .post("/api/auth/register");
        if (response.statusCode() == 200) {
            this.accessToken = response.path("accessToken").toString().substring(7);
        }
        return response;
    }

    @Step("Login user")
    public Response login() {
        Response response = given()
                .spec(getBaseSpec())
                .and()
                .body(this.toJSON())
                .when()
                .post("/api/auth/login");

        if (response.statusCode() == 200) {
            this.accessToken = response.path("accessToken").toString().substring(7);
        }
        return response;

    }

    @Step("Change user's fields")
    public Response changingDataOfAuthUser() {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(this.toJSON())
                .auth().oauth2(accessToken)
                .when()
                .patch("/api/auth/user");
    }

    @Step("Create order")
    public Response makeOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(order.toJSON())
                .auth().oauth2(accessToken)
                .when()
                .post("/api/orders");
    }

    @Step("Get order current user")
    public Response getOrderCurrentUser() {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .when()
                .get("/api/orders");
    }

    @Step("delete user")
    public Response deleteUser() {
        if (this.accessToken != "") {
            return given()
                    .spec(getBaseSpec())
                    .auth().oauth2(accessToken)
                    .delete("/api/auth/user");
        }
        return null;
    }

}
