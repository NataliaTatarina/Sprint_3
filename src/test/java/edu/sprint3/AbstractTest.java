package edu.sprint3;

import edu.sprint3.pojo.Courier;
import edu.sprint3.pojo.CourierAuthorization;
import io.restassured.RestAssured;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AbstractTest {

    protected RequestSpecification requestSpec = RestAssured.given()
            .baseUri("https://qa-scooter.praktikum-services.ru/")
            .header("Content-type", "application/json");
    protected RequestSpecification requestSpecWithResponseLog = RestAssured.given()
            .filter(new ResponseLoggingFilter())
            .baseUri("https://qa-scooter.praktikum-services.ru/")
            .header("Content-type", "application/json");
    protected String testLogin = "TestLogin" + RandomStringUtils.randomAlphabetic(5);
    protected String testPassword = "TestPass" + RandomStringUtils.randomAlphabetic(5);
    protected String testFirstName = "TestFirstName" + RandomStringUtils.randomAlphabetic(5);
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

}
