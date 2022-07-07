package edu.sprint3;

import edu.sprint3.pojo.Courier;
import edu.sprint3.pojo.CourierForCreateWithoutLogin;
import edu.sprint3.pojo.CourierForCreateWithoutPassword;
import edu.sprint3.pojo.CourierForLogin;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

public class CreateCourierTest extends AbstractTest{
    private boolean courierCreated;

    @Before
    public void setUp() {
         courierCreated = false;
    }

    @After
    public void deleteCreatedCourier() {
        if ( courierCreated)
        {
            // определяем id курьера
           int courierIdFromResponse = given()
                   .spec(baseUri)
                    .header("Content-type", "application/json")
                    .and()
                    .body(courier)
                    .when()
                    .post("/api/v1/courier/login")
                    .then().extract().body().path("id");
            // удалаяем курьера
            given()
                    .spec(baseUri)
                    .header("Content-type", "application/json")
                    .when()
                    .delete("/api/v1/courier/" + courierIdFromResponse)
                    .then().statusCode(200);
        }
    }
    // Курьера можно создать
    @Test
    public void createCourierSuccessTest() {
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);
        courierCreated = true;
    }

    // Нельзя создать 2 одинаковых курьеров
    @Test
    public void createTwoEqualCouriersFailsTest() {
        Courier courierSecond = new Courier(testLogin, testPassword, testFirstName);
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierSecond)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        courierCreated = true;
    }

    // Если создать пользователя с логином, который уже есть, возвращается ошибка
    @Test
       public void createCouriersWithSameLoginFailTest() {
        Courier courierSecond = new Courier(testLogin, testPassword+testPassword, testFirstName+testFirstName);
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierSecond)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        courierCreated = true;
    }

    // Запрос возвращает правильный код ответа
    @Test
    public void checkCreateCourierSuccessStatusCodeTest() {
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);
        courierCreated = true;
    }

    // Успешный запрос возвращает ok:true
    @Test
    public void checkCourierSuccessReturnsOkTest() {
        MatcherAssert.assertThat(
                given()
                        .spec(baseUri)
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier").then().extract().body().path("ok"),
                is(true)
        );
        courierCreated = true;
    }

    // Чтобы создать курьера, надо передать в ручку обязательные поля
    @Test
    public void createCourierWithEmptyLoginTest() {
        Courier courierWithEmptyLogin = new Courier(null, testPassword, testFirstName);
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierWithEmptyLogin)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithEmptyPasswordTest() {
        Courier courierWithEmptyPassword = new Courier(testLogin, null, testFirstName);
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierWithEmptyPassword)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithEmptyFirstnameTest() {
        Courier courierWithEmptyFirstName = new Courier(testLogin, testPassword, null);
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierWithEmptyFirstName)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);
        courierCreated = true;
    }

    // Если одного поля нет, запрос возвращает ошибку
    @Test
    public void createCourierWithoutLoginTest() {
        CourierForCreateWithoutLogin courierWithoutLogin = new CourierForCreateWithoutLogin(testPassword, testFirstName);
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierWithoutLogin)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithoutPasswordTest() {
        CourierForCreateWithoutPassword courierWithoutPassword = new CourierForCreateWithoutPassword(testPassword, testFirstName);
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierWithoutPassword)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithoutFirstNameTest() {
        CourierForLogin courierWithoutFirstName = new CourierForLogin(testLogin, testPassword);
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierWithoutFirstName)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);
                courierCreated = true;
     }
}
