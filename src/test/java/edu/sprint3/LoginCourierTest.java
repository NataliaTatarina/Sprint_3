package edu.sprint3;

import edu.sprint3.pojo.CourierForLogin;
import edu.sprint3.pojo.CourierForLoginWithoutLogin;
import edu.sprint3.pojo.CourierForLoginWithoutPassword;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest extends AbstractTest {

    private int courierIdFromResponse;

    @Before
    public void setUp() {
        // создаем учетную запись курьера
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);
    }

    @After
    public void deleteCreatedCourier() {
        // определяем id курьера
        courierIdFromResponse = given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierForLogin)
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

    // Курьер может авторизоваться
    @Test
    public void loginCourierSuccessTest() {
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierForLogin)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(200);
    }

    // Для авторизации надо заполнить все обязательнеы поля
    @Test
    public void loginWithEmptyLoginFailsTest() {
        CourierForLogin courierForLoginWithEmptyLogin = new CourierForLogin(null, testPassword);
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierForLoginWithEmptyLogin)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    // Отличие от документации
    public void loginWithEmptyPasswordFailsTest() {
        CourierForLogin courierForLoginWithEmptyPassword = new CourierForLogin(testLogin, null);
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierForLoginWithEmptyPassword)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(504);
    }

    // Система вернет ошибку, если неправильно указать логин и пароль
    @Test
    public void loginWithWrongPasswordFailsTest() {
        CourierForLogin courierForLoginWithWrongPassword = new CourierForLogin(testLogin, testPassword + testPassword);
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierForLoginWithWrongPassword)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    // Если какого-то поля нет, то запрос возвращает ошибку
    @Test
    public void loginWithoutLoginFailsTest() {
        CourierForLoginWithoutLogin courierForLoginWithoutLogin = new CourierForLoginWithoutLogin(testPassword);
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierForLoginWithoutLogin)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    // Отличие от документации
    public void loginWithoutPasswordFailsTest() {
        CourierForLoginWithoutPassword courierForLoginWithoutPassword = new CourierForLoginWithoutPassword(testLogin);
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierForLoginWithoutPassword)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(504);
    }

    // Если авторизироваться под несуществующим пользователем, запрос возвращает ошибку
    @Test
    public void loginWithWrongLoginFailsTest() {
        CourierForLogin courierForLoginWithWrongUser = new CourierForLogin(testLogin + testLogin, testPassword);
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierForLoginWithWrongUser)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    // Успешный запрос возвращает id
    @Test
    public void checkLoginResponseReturnsIdTest() {
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courierForLogin)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }
}
