package edu.sprint3;

import edu.sprint3.pojo.Courier;
import edu.sprint3.pojo.CourierForLogin;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AbstractTest {

   protected RequestSpecification baseUri = RestAssured.given().baseUri("https://qa-scooter.praktikum-services.ru/");

   protected String testLogin = "TestLogin"+ RandomStringUtils.randomAlphabetic(5);
   protected String testPassword = "TestPass"+RandomStringUtils.randomAlphabetic(5);
   protected String testFirstName = "TestFirstName"+RandomStringUtils.randomAlphabetic(5);
   protected Courier courier = new Courier(testLogin, testPassword, testFirstName);
   protected CourierForLogin courierForLogin = new CourierForLogin(testLogin, testPassword);

   protected String[] color;
   protected String firstName = "TestFirstName";
   protected String lastName = "TestLastName";
   protected String address = "TestAddress";
   protected String metroStation = "1";
   protected String phone = "TestPhone";
   protected int rentTime = 1;
   protected String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
   protected String comment = "TestComment";
}
