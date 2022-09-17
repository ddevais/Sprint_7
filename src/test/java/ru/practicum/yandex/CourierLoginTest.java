package ru.practicum.yandex;

import org.junit.*;
import ru.practicum.yandex.courier.Courier;
import ru.practicum.yandex.courier.CourierClient;
import ru.practicum.yandex.courier.CourierLogin;

import static org.junit.Assert.*;

public class CourierLoginTest {

CourierClient courierClient;
Courier courier;
CourierLogin courierLogin;
public int courierId;


    @Before
    public void setup(){
        courierClient = new CourierClient();
        courier = Courier.getRandomCourier();
        courierClient.create(courier);
        courierId = courierClient.login(CourierLogin.getLogin(courier))
                .statusCode(200)
                .extract()
                .path("id");
    }
    @After
    public void teardown() {
        courierClient.delete(courierId);
    }
    @Test

    public void authorizationWithoutLoginExpectError(){
        courierLogin = CourierLogin.getLogin(courier);
        courierLogin.setLogin("");
        String error = courierClient.login(courierLogin)
                .statusCode(400).extract().path("message");
        assertEquals("Недостаточно данных для входа", error);


    }
    @Test

    public void authorizationWithoutPassExpectError(){
        courierLogin = CourierLogin.getLogin(courier);
        courierLogin.setPassword("");
        String error = courierClient.login(courierLogin)
                .statusCode(400).extract().path("message");
        assertEquals("Недостаточно данных для входа", error);


    }
    @Test

    public void authorizationWithInvalidPassExpectError(){
        courierLogin = CourierLogin.getLogin(courier);
        courierLogin.setPassword(courierLogin.getPassword() + "0");
        String error = courierClient.login(courierLogin)
                .statusCode(404).extract().path("message");
        assertEquals("Учетная запись не найдена", error);
    }

    @Test

    public void authorizationWithInvalidLoginExpectError(){
        courierLogin = CourierLogin.getLogin(courier);
        courierLogin.setLogin(courierLogin.getLogin() + "0");
        String error = courierClient.login(courierLogin)
                .statusCode(404).extract().path("message");
        assertEquals("Учетная запись не найдена", error);
    }
    @Test

    public void authorizationWithInvalidPassAndLoginExpectError(){

        courierLogin = new CourierLogin("*", "*");
        courierLogin.setPassword(courierLogin.getPassword() + "0");
        String error = courierClient.login(courierLogin)
                .statusCode(404).extract().path("message");

        assertEquals("Учетная запись не найдена", error);
    }
}