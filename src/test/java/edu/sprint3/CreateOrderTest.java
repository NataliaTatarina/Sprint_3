package edu.sprint3;

import edu.sprint3.pojo.CreateOrder;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(Parameterized.class)

public class CreateOrderTest extends AbstractTest {


    private int orderTrack;
    private String[] colorList;
    private final String colorBlack;
    private final String colorGrey;
    private final int expectedResponseStatus;

    public CreateOrderTest(String colorBlack, String colorGrey, int expectedResponseStatus) {
        this.colorBlack = colorBlack;
        this.colorGrey = colorGrey;
        this.expectedResponseStatus = expectedResponseStatus;
    }

    @Parameterized.Parameters
    public static Object[][] getColorsData() {
        return new Object[][]{
                {"BLACK", null, 201},
                {null, "GREY", 201},
                {null, null, 201},
                {"BLACK", "GREY", 201},
                {"GREY", "BLACK", 201},
                {"RED", "GREEN", 201}
        };
    }

    @After
    public void deleteOrder() {
        // Удалаяем заказ
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .when()
                .queryParam("track", orderTrack)
                .put("/api/v1/orders/cancel")
                .then().statusCode(200);
    }

    // Можно указать один из цветов — BLACK или GREY
    // Можно указать оба цвета
    // Можно совсем не указывать цвет
    @Test
    public void createOrderWithDifferentColorsTest() {
        colorList = new String[]{colorBlack};
        CreateOrder createOrder = new CreateOrder(firstName, lastName, address, metroStation, phone,
                rentTime, deliveryDate, comment, colorList);
        Response response = given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(createOrder)
                .when()
                .post("/api/v1/orders");
        response.then().statusCode(expectedResponseStatus);
        // Определяем трэк для того, чтобы потом удалить заказ по завершении теста
        orderTrack = response.then().extract().body().path("track");
    }


    // Тело ответа содержит track
    @Test
    public void checkCreateOrderResponseContentsTrackTest() {
        CreateOrder createOrder = new CreateOrder(firstName, lastName, address, metroStation, phone,
                rentTime, deliveryDate, comment, null);
        Response response = given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(createOrder)
                .when()
                .post("/api/v1/orders");
        MatcherAssert.assertThat(response.then().extract().body().path("track"), notNullValue());
        // Определяем трэк для того, чтобы потом удалить заказ по завершении теста
        orderTrack = response.then().extract().body().path("track");
    }

}
