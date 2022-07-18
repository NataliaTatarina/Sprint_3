package edu.sprint3;

import edu.sprint3.pojo.Order;
import edu.sprint3.pojo.SingleOrder;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderByTrackTest extends AbstractTest {
    private int orderTrack;

    Order order = new Order()
            .setFirstName(firstName)
            .setLastName(lastName)
            .setAddress(address)
            .setMetroStation(Integer.toString(metroStation))
            .setPhone(phone)
            .setRentTime(rentTime)
            .setDeliveryDate(deliveryDate)
            .setColor(color)
            .setComment(comment);

    // Успешный запрос возвращает объект с заказом
    @Test
    public void getOrderByTrackSuccessTest() {
        orderTrack = given()
                .spec(requestSpec)
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then().extract().body().path("track");
        System.out.println("orderTrack " + orderTrack);

        SingleOrder singleOrder =
                given()
                        .spec(requestSpec)
                        .when()
                        .queryParam("t", orderTrack)
                        .get("/api/v1/orders/track")
                        .body().as(SingleOrder.class);
        MatcherAssert.assertThat(singleOrder, notNullValue());

        given()
                .spec(requestSpec)
                .when()
                .queryParam("track", orderTrack)
                .put("/api/v1/orders/cancel")
                .then().statusCode(SC_OK);

    }

    // Запрос без номера заказа возвращает ошибку
    @Test
    public void getOrderByTrackWithoutTrackFailsTest() {
        given()
                .spec(requestSpec)
                .when()
                .get("/api/v1/orders/track")
                .then().assertThat().body("message", equalTo("Недостаточно данных для поиска"))
                .and()
                .statusCode(SC_BAD_REQUEST);

    }

    // Запрос с несуществующим заказом возвращает ошибку
    @Test
    public void getOrderByTrackWithWrongTrackFailsTest() {
        given()
                .spec(requestSpec)
                .when()
                .queryParam("t", 0)
                .get("/api/v1/orders/track")
                .then().assertThat().body("message", equalTo("Заказ не найден"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }
}
