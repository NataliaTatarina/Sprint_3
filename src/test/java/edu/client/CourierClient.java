package edu.client;


import edu.sprint3.pojo.Courier;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;


public class CourierClient {


    // Создать курьера
    public static void createCourierProc(RequestSpecification requestSpec, Courier courier) {
        given()
                .spec(requestSpec)
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(SC_CREATED);
    }

    // Определить id курьера
    public static int getCourierIdProc(RequestSpecification requestSpec, Courier courier) {
        ;
        return given()
                .filter(new ResponseLoggingFilter())
                .spec(requestSpec)
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then().extract().body().path("id");

    }

    // Удалить курьера
    public static void deleteCourierProc(RequestSpecification requestSpec, int courierIdFromResponse) {
        given()
                .spec(requestSpec)
                .when()
                .delete("/api/v1/courier/" + courierIdFromResponse)
                .then().statusCode(SC_OK);
    }
}
