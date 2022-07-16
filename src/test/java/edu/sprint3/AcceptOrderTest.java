package edu.sprint3;

import edu.sprint3.pojo.Order;
import edu.sprint3.pojo.SingleOrder;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AcceptOrderTest extends AbstractTest {
    private int courierIdFromResponse;
    private int orderId;
    private int orderTrack;

    boolean orderCanBeDeleted;
    Order order = new Order( null, null,  firstName,
            lastName,   address, Integer.toString(metroStation),
            phone,   rentTime,  deliveryDate,
            null, null, color,  comment,
            null,  null,  null,
            null,  null, null);
    @Before
    public void setUp() {
        orderCanBeDeleted = true;
        //Создать курьера
        createCourierProc();
        // Определить id курьера
        courierIdFromResponse = getCourierIdProc();
        System.out.println(courierIdFromResponse);
        // Создать заказ и определить его номер - track
        orderTrack = given()
                .spec(requestSpec)
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then().extract().body().path("track");
        System.out.println(orderTrack);
        // Узнать id заказа по его track
        SingleOrder singleOrder =
                given()
                        .spec(requestSpec)
                        .header("Content-type", "application/json")
                        .when()
                        .queryParam("t", orderTrack)
                        .get("/api/v1/orders/track")
                        .body()
                        .as(SingleOrder.class);
        MatcherAssert.assertThat(singleOrder, notNullValue());
        orderId = singleOrder.getOrder().getId();
        System.out.println("orderId"+orderId);
    }

    @After
    public void deleteCourierAndOrder() {
        // Принятый заказ нельзя удалить
        if (orderCanBeDeleted) {
            given()
                    .spec(requestSpec)
                    .when()
                    .queryParam("track", orderTrack)
                    .put("/api/v1/orders/cancel")
                    .then().statusCode(SC_OK);
        } else {
            given()
                    .spec(requestSpec)
                    .when()
                    .queryParam("track", orderTrack)
                    .put("/api/v1/orders/cancel")
                    .then().statusCode(SC_CONFLICT);
            System.out.println(" Принятый заказ не удалить");
        }
        // Удаляем курьера
        deleteCourierProc(courierIdFromResponse);
    }

    // Успешный запрос возвращает ok: true
    @Test
    public void checkOrderSuccessReturnsOkTest() {
        MatcherAssert.assertThat(
                given()
                        .spec(requestSpec)
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
                .spec(requestSpec)
                .when()
                .put("/api/v1/orders/accept/" + orderId)
                .then().assertThat().body("message", equalTo("Недостаточно данных для поиска"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    // Если передать неверный id курьера, запрос вернёт ошибку
    @Test
    public void acceptOrderWithWrongCourierIdTest() {
        given()
                .spec(requestSpec)
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
                .spec(requestSpec)
                .when()
                .queryParam("courierId", courierIdFromResponse)
                .put("/api/v1/orders/accept/")
                .then().assertThat().body("message", equalTo("Not Found."))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    // Если передать неверный номер заказа, запрос вернёт ошибку
    @Test
    public void acceptOrderWithWrongOrderIdTest() {
        given()
                .spec(requestSpec)
                .when()
                .queryParam("courierId", courierIdFromResponse)
                .put("/api/v1/orders/accept/" + 0)
                .then().assertThat().body("message", equalTo("Заказа с таким id не существует"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }
}
