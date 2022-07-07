package edu.sprint3;

import edu.sprint3.pojo.OrdersList;
import com.google.gson.Gson;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class OrdersListTest  extends  AbstractTest{
    @Test
    @DisplayName("getOrdersListTest") // имя теста
  //  @Description("getOrdersListTestDescription") // описание теста
    public void getOrdersListTest() {
        OrdersList ordersList =
                given()

                        .spec(baseUri)
                        .header("Content-type", "application/json")
                        .when()
                        .get("/api/v1/orders")
                        .body()
                        .as(OrdersList.class);
        MatcherAssert.assertThat(ordersList,
                notNullValue());
        Gson gson = new Gson();
        String json = gson.toJson(ordersList);
        System.out.println(json);
        System.out.println(ordersList.getPageInfo().getTotal());
    }
}
