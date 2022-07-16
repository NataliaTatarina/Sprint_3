package edu.sprint3;

import edu.sprint3.pojo.OrdersList;
import com.google.gson.Gson;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class OrdersListTest  extends  AbstractTest{
    private Filter requestFilter;
    private  Filter responseFilter;
    @Test
    @DisplayName("getOrdersListTest") // имя теста

    public void getOrdersListTest() {
    requestFilter = new RequestLoggingFilter();
    responseFilter= new ResponseLoggingFilter();
        OrdersList ordersList =
                given()
                        .filters(List.of(requestFilter, responseFilter))
                        .spec(requestSpec)
                        .when()
                        .get("/api/v1/orders")
                        .body()
                        .as(OrdersList.class);
        MatcherAssert.assertThat(ordersList,
                notNullValue());
    }
}
