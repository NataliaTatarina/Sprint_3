package edu.sprint3;

import edu.client.CourierClient;
import edu.sprint3.pojo.Courier;
import edu.sprint3.pojo.CourierAuthorization;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest extends AbstractTest {

    // Курьер может авторизоваться
    @Test
    public void loginCourierSuccessTest() {
        CourierClient.createCourierProc(requestSpec, courier);
        given()
                .spec(requestSpec)
                .header("Content-type", "application/json")
                .and()
                .body(courierAuthorization)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(SC_OK);
        CourierClient.deleteCourierProc(requestSpec, CourierClient.getCourierIdProc(requestSpec, courier));
    }

    // Для авторизации надо заполнить все обязательные поля
    // По очереди передаем NULL
    @Test
    public void loginWithEmptyLoginFailsTest() {
        CourierAuthorization сourierAuthorizationWithEmptyLogin = new CourierAuthorization(null, testPassword);
        given()
                .spec(requestSpec)
                .header("Content-type", "application/json")
                .and()
                .body(сourierAuthorizationWithEmptyLogin)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    // Отличие от документации
    public void loginWithEmptyPasswordFailsTest() {
        CourierAuthorization сourierAuthorizationWithEmptyPassword = new CourierAuthorization(testLogin, null);
        given()
                .spec(requestSpec)
                .header("Content-type", "application/json")
                .and()
                .body(сourierAuthorizationWithEmptyPassword)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(SC_GATEWAY_TIMEOUT);
    }

    // Система вернет ошибку, если неправильно указать логин и пароль
    @Test
    public void loginWithWrongPasswordFailsTest() {
        CourierAuthorization courierAuthorizationWithWrongPassword = new CourierAuthorization(testLogin, testPassword + testPassword);
        given()
                .spec(requestSpec)
                .header("Content-type", "application/json")
                .and()
                .body(courierAuthorizationWithWrongPassword)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    // Если какого-то поля нет, то запрос возвращает ошибку
    @Test
    public void loginWithoutLoginFailsTest() {
        Courier courierWithoutLoginAndFirstName = new Courier(null, testPassword, null);
        given()
                .spec(requestSpec)
                .header("Content-type", "application/json")
                .and()
                .body(courierWithoutLoginAndFirstName)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    // Отличие от документации
    public void loginWithoutPasswordFailsTest() {
        Courier courierWithoutPasswordAndFirstName = new Courier(testLogin, null, null);
        given()
                .spec(requestSpec)
                .header("Content-type", "application/json")
                .and()
                .body(courierWithoutPasswordAndFirstName)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(SC_GATEWAY_TIMEOUT);
    }

    // Если авторизироваться под несуществующим пользователем, запрос возвращает ошибку
    @Test
    public void loginWithWrongLoginFailsTest() {
        CourierAuthorization courierAuthorizationWithWrongUser = new CourierAuthorization(testLogin + testLogin, testPassword);
        given()
                .spec(requestSpec)
                .header("Content-type", "application/json")
                .and()
                .body(courierAuthorizationWithWrongUser)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    // Успешный запрос возвращает id
    @Test
    public void checkLoginResponseReturnsIdTest() {
        CourierClient.createCourierProc(requestSpec, courier);
        given()
                .spec(requestSpec)
                .header("Content-type", "application/json")
                .and()
                .body(courierAuthorization)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK);
        CourierClient.deleteCourierProc(requestSpec, CourierClient.getCourierIdProc(requestSpec, courier));
    }
}
