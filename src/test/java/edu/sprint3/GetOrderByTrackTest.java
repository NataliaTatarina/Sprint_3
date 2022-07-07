package edu.sprint3;

import edu.sprint3.pojo.CreateOrder;
import edu.sprint3.pojo.SingleOrder;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderByTrackTest extends AbstractTest{
    private int orderTrack;
    CreateOrder createOrder = new CreateOrder(firstName, lastName, address, metroStation, phone,
            rentTime, deliveryDate, comment, color);
    @Before
    public void setUp() {
           orderTrack = given()
                   .spec(baseUri)
                .header("Content-type", "application/json")
                 .and()
                .body(createOrder)
                .when()
                .post("/api/v1/orders")
                .then().extract().body().path("track");
    }

    @After
    public void deleteOrder () {
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .when()
                .queryParam("track", orderTrack)
                .put("/api/v1/orders/cancel")
                .then().statusCode(200);
    }

    // Успешный запрос возвращает объект с заказом
    @Test
    public void getOrderByTrackSuccessTest (){
        SingleOrder singleOrder =
                given()
                        .spec(baseUri)
                        .header("Content-type", "application/json")
                        .when()
                        .queryParam("t", orderTrack)
                        .get("/api/v1/orders/track")
                        .body().as(SingleOrder.class);
        MatcherAssert.assertThat(singleOrder, notNullValue());
    }

    // Запрос без номера заказа возвращает ошибку
    @Test
    public void getOrderByTrackWithoutTrackFailsTest() {
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders/track")
                .then().assertThat().body("message", equalTo("Недостаточно данных для поиска"))
                .and()
                .statusCode(400);
    }

    // Запрос с несуществующим заказом возвращает ошибку
    @Test
    public void getOrderByTrackWithWrongTrackFailsTest() {
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .when()
                .queryParam("t", 0)
                .get("/api/v1/orders/track")
                .then().assertThat().body("message", equalTo("Заказ не найден"))
                .and()
                .statusCode(404);
    }
}
