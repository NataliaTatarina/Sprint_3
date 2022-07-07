package edu.sprint3;

import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class DeleteCourierTest extends AbstractTest{

   private boolean courierExists;

    @Before
    public void setUp() {

        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);
        courierExists = true;
   }

   @After
          public void deleteCreatedCourier ()
   {
              if (courierExists)
       {
           // определяем id курьера
           int courierIdFromResponse = given()
                   .spec(baseUri)
                   .header("Content-type", "application/json")
                   .and()
                   .body(courier)
                   .when()
                   .post("/api/v1/courier/login")
                   .then().extract().body().path("id");
           // удалаяем курьера
           given()
                   .spec(baseUri)
                   .header("Content-type", "application/json")
                   .when()
                   .delete("/api/v1/courier/" + courierIdFromResponse)
                   .then().statusCode(200);
       }

   }
    // Успешный запрос возвращает ok: true
    @Test
    public void deleteCourierCorrectTest(){
        // определяем id курьера
        int courierIdFromResponse = given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then().extract().body().path("id");
        // удалаяем курьера
        MatcherAssert.assertThat(
                given()
                        .spec(baseUri)
                        .header("Content-type", "application/json")
                        .when()
                        .delete("/api/v1/courier/" + courierIdFromResponse).
                        then().extract().body().path("ok"),
                is(true));
                courierExists = false;
           }

    // Неуспешный запрос возвращает соответствующую ошибку
    @Test
    public void deleteCourierWrongRequestFailsTest()
    {
        // удалаяем курьера
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/:")
                .then().statusCode(500);
    }

    // Если отправить запрос без id, вернётся ошибка
    @Test
    public void deleteCourierWithoutIdFailsTest()
    {
        // удалаяем курьера
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/")
                .then().statusCode(404);
    }

    // Если отправить запрос с несуществующим id, вернётся ошибка
    @Test
    public void deleteCourierWithWrongIdFailsTest()
    {
        // удалаяем курьера
        given()
                .spec(baseUri)
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/" + "0")
                .then().statusCode(404);

    }
}
