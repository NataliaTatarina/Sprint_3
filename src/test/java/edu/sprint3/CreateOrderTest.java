package edu.sprint3;

import edu.sprint3.pojo.Order;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(Parameterized.class)

public class CreateOrderTest extends AbstractTest {


   private int orderTrack;
   private final String[] colorList;
   public CreateOrderTest(String[] colorList) {
       this.colorList = colorList;
   }


    @Parameterized.Parameters
    public static Object[][] getColorsData() {
        return new Object[][]{
                {new String[] { "RED", "GREEN" }},
                {new String[] { "BLACK", null }},
                {new String[]{ null, "GREY" }},
                {new String[] { null, null }},
                {new String[] { "BLACK", "GREY" }}
        };
    }

    @After
    public void deleteOrder() {
        // Удалаяем заказ
        given()
                .spec(requestSpec)
                .when()
                .queryParam("track", orderTrack)
                .put("/api/v1/orders/cancel")
                .then().statusCode(SC_OK);
    }

    // Можно указать один из цветов — BLACK или GREY
    // Можно указать оба цвета
    // Можно совсем не указывать цвет
    // Тело ответа содержит track
    @Test
    public void createOrderWithDifferentColorsTest() {
        Order orderExp = new Order( null, null,   firstName,
                 lastName,   address, Integer.toString(metroStation),
                 phone,   rentTime,  deliveryDate,
         null, null, colorList,  comment,
         null,  null,  null,
         null,  null, null);
        Response response = given()
                .spec(requestSpec)
                .and()
                .body(orderExp)
                .when()
                .post("/api/v1/orders");
        response.then().statusCode(SC_CREATED);
        // Определяем трэк для того, чтобы потом удалить заказ по завершении теста
        orderTrack = response.then().extract().body().path("track");
        // Проверяем, что тело ответа содержит track
        MatcherAssert.assertThat(response.then().extract().body().path("track"), notNullValue());
    }
}
