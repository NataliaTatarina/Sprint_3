package edu.sprint3;

import edu.sprint3.pojo.CreateOrder;
import edu.sprint3.pojo.SingleOrder;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AcceptOrderTest extends AbstractTest {
    private int courierIdFromResponse;
    private int orderId;
    private int orderTrack;

    boolean orderCanBeDeleted;
    CreateOrder createOrder = new CreateOrder(firstName, lastName, address, metroStation, phone,
            rentTime, deliveryDate, comment, color);

    @Before
    public void setUp() {
        orderCanBeDeleted = true;
        //Создать курьера
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);
        // Определить id курьера
        courierIdFromResponse = given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then().extract().body().path("id");
        System.out.println(courierIdFromResponse);
        // Создать заказ и определить его номер - track
        orderTrack = given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(createOrder)
                .when()
                .post("/api/v1/orders")
                .then().extract().body().path("track");
        System.out.println(orderTrack);
        // Узнать id заказа по его track
        SingleOrder singleOrder =
                given()
                        .spec(baseUri)
                        .header("Content-type", "application/json")
                        .when()
                        .queryParam("t", orderTrack)
                        .get("/api/v1/orders/track")
                        .body()
                        .as(SingleOrder.class);
        MatcherAssert.assertThat(singleOrder, notNullValue());
        orderId = singleOrder.getOrder().getId();
        System.out.println(orderId);
    }

    @After
    public void deleteCourier() {
        // Принятый заказ нельзя удалить
        if (orderCanBeDeleted) {
            given()
                    .spec(baseUri)
                    .header("Content-type", "application/json")
                    .when()
                    .queryParam("track", orderTrack)
                    .put("/api/v1/orders/cancel")
                    .then().statusCode(200);
        } else {
            given()
                    .spec(baseUri)
                    .header("Content-type", "application/json")
                    .when()
                    .queryParam("track", orderTrack)
                    .put("/api/v1/orders/cancel")
                    .then().statusCode(409);
            System.out.println(" Принятый заказ не удалить");
        }
        // Удаляем курьера
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/" + courierIdFromResponse)
                .then().statusCode(200);
    }

    // Успешный запрос возвращает ok: true
    @Test
    public void checkOrderSuccessReturnsOkTest() {
        MatcherAssert.assertThat(
                given()
                        .spec(baseUri)
                        .header("Content-type", "application/json")
                        .when()
                        .queryParam("courierId", courierIdFromResponse)
                        .put("/api/v1/orders/accept/" + orderId)
                        .then()
                        .extract().body().path("ok"),
                is(true));
        orderCanBeDeleted = false;
    }

    // Если не передать id курьера, запрос вернёт ошибку
    @Test
    public void acceptOrderWithoutCourierIdTest() {
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .when()
                .put("/api/v1/orders/accept/" + orderId)
                .then().assertThat().body("message", equalTo("Недостаточно данных для поиска"))
                .and()
                .statusCode(400);
    }

    // Если передать неверный id курьера, запрос вернёт ошибку
    @Test
    public void acceptOrderWithWrongCourierIdTest() {
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .when()
                .queryParam("courierId", 0)
                .put("/api/v1/orders/accept/" + orderId)
                .then().assertThat().body("message", equalTo("Курьера с таким id не существует"))
                .and()
                .statusCode(404);
    }

    // Если не передать номер заказа, запрос вернёт ошибку
    // Без номера заказа выдает 404
    @Test
    public void acceptOrderWithoutOrderIdTest() {
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .when()
                .queryParam("courierId", courierIdFromResponse)
                .put("/api/v1/orders/accept/")
                .then().assertThat().body("message", equalTo("Not Found."))
                .and()
                .statusCode(404);
    }

    // Если передать неверный номер заказа, запрос вернёт ошибку
    @Test
    public void acceptOrderWithWrongOrderIdTest() {
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .when()
                .queryParam("courierId", courierIdFromResponse)
                .put("/api/v1/orders/accept/" + 0)
                .then().assertThat().body("message", equalTo("Заказа с таким id не существует"))
                .and()
                .statusCode(404);
    }
}
