package edu.sprint3;

import edu.client.CourierClient;
import edu.sprint3.pojo.Courier;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest extends AbstractTest {
    private boolean courierCreated;

    @Before
    public void setUp() {
        courierCreated = false;
    }

    @After
    public void deleteCreatedCourier() {
        if (courierCreated) {
            // Определяем id курьера и удалаяем курьера
            CourierClient.deleteCourierProc(requestSpec, CourierClient.getCourierIdProc(requestSpec, courier));
        }
    }

    // Курьера можно создать
    @Test
    public void createCourierSuccessTest() {
        given()
                .spec(requestSpec)
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(SC_CREATED);
        courierCreated = true;
    }

    // Нельзя создать 2 одинаковых курьеров
    @Test
    public void createTwoEqualCouriersFailsTest() {
        Courier courierSecond = new Courier(testLogin, testPassword, testFirstName);
        given()
                .spec(requestSpec)
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(SC_CREATED);
        given()
                .spec(requestSpec)
                .header("Content-type", "application/json")
                .and()
                .body(courierSecond)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        courierCreated = true;
    }

    // Если создать пользователя с логином, который уже есть, возвращается ошибка
    @Test
    public void createCouriersWithSameLoginFailTest() {
        Courier courierSecond = new Courier(testLogin, testPassword + testPassword, testFirstName + testFirstName);
        given()
                .spec(requestSpec)
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(SC_CREATED);
        given()
                .spec(requestSpec)
                .header("Content-type", "application/json")
                .and()
                .body(courierSecond)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        courierCreated = true;
    }

    // Запрос возвращает правильный код ответа
    @Test
    public void checkCreateCourierSuccessStatusCodeTest() {
        given()
                .spec(requestSpec)
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(SC_CREATED);
        courierCreated = true;
    }

    // Успешный запрос возвращает ok:true
    @Test
    public void checkCourierSuccessReturnsOkTest() {
        MatcherAssert.assertThat(
                given()
                        .spec(requestSpec)
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
                .spec(requestSpec)
                .and()
                .body(courierWithEmptyLogin)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithEmptyPasswordTest() {
        Courier courierWithEmptyPassword = new Courier(testLogin, null, testFirstName);
        given()
                .spec(requestSpec)
                .and()
                .body(courierWithEmptyPassword)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithEmptyFirstnameTest() {
        Courier courierWithEmptyFirstName = new Courier(testLogin, testPassword, null);
        given()
                .spec(requestSpec)
                .and()
                .body(courierWithEmptyFirstName)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(SC_CREATED);
        courierCreated = true;
    }

    // Если одного поля нет, запрос возвращает ошибку
    @Test
    public void createCourierWithoutLoginTest() {
        Courier courierWithoutLogin = new Courier(null, testPassword, testFirstName);
        given()
                .spec(requestSpec)
                .and()
                .body(courierWithoutLogin)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithoutPasswordTest() {
        Courier courierWithoutPassword = new Courier(testLogin, null, testFirstName);
        given()
                .spec(requestSpec)
                .and()
                .body(courierWithoutPassword)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithoutFirstNameTest() {
        Courier courierWithoutFirstName = new Courier(testLogin, testPassword, null);
        given()
                .spec(requestSpec)
                .and()
                .body(courierWithoutFirstName)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(SC_CREATED);
        courierCreated = true;
    }
}
