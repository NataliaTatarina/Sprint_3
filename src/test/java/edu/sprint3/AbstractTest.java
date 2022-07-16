package edu.sprint3;

import edu.sprint3.pojo.Courier;
import edu.sprint3.pojo.CourierAuthorization;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;


public class AbstractTest {

   protected RequestSpecification requestSpec = RestAssured.given()
           .baseUri("https://qa-scooter.praktikum-services.ru/")
           .header("Content-type", "application/json");

   protected String testLogin = "TestLogin"+ RandomStringUtils.randomAlphabetic(5);
   protected String testPassword = "TestPass"+RandomStringUtils.randomAlphabetic(5);
   protected String testFirstName = "TestFirstName"+RandomStringUtils.randomAlphabetic(5);
   protected Courier courier = new Courier(testLogin, testPassword, testFirstName);

   protected CourierAuthorization courierAuthorization = new CourierAuthorization(testLogin, testPassword);
   protected String[] color;
   protected String firstName = "TestFirstName";
   protected String lastName = "TestLastName";
   protected String address = "TestAddress";
   protected Integer metroStation = 1;
   protected String phone = "TestPhone";
   protected Integer rentTime = 1;
   protected String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
   protected String comment = "TestComment";

   // Создать курьера
   public void createCourierProc() {
      given()
              .spec(requestSpec)
              .and()
              .body(courier)
              .when()
              .post("/api/v1/courier")
              .then()
              .statusCode(SC_CREATED);}
      // Определить id курьера
   public int getCourierIdProc ()
   {
      return  given()
              .spec(requestSpec)
              .and()
              .body(courier)
              .when()
              .post("/api/v1/courier/login")
              .then().extract().body().path("id");

   }
      // Удалить курьера
      public void deleteCourierProc (int courierIdFromResponse) {
         given()
                 .spec(requestSpec)
                 .when()
                 .delete("/api/v1/courier/" + courierIdFromResponse)
                 .then().statusCode(SC_OK);
      }


}
