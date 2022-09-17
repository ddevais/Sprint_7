package ru.practicum.yandex;

import org.junit.Before;
import org.junit.Test;
import ru.practicum.yandex.courier.Courier;
import ru.practicum.yandex.courier.CourierClient;
import ru.practicum.yandex.courier.CourierLogin;
import static org.junit.Assert.*;

public class CourierCreateTest {
    Courier courier;
    CourierClient courierClient;
    private int courierId;


    @Before
    public void setUp() {
        courier = Courier.getRandomCourier();
        courierClient = new CourierClient();
    }

    @Test

     public void createNewCourierProfileAndAuthorization() {

        boolean isOk = courierClient.create(courier)
                .statusCode(201).extract().path("ok");
       courierId = courierClient.login(CourierLogin.getLogin(courier))
               .statusCode(200)
                .extract()
                .path("id");
        assertTrue(isOk);
        assertNotEquals(0, courierId);
        courierClient.delete(courierId);


    }
    @Test

    public void createTwoSameCouriersExpectError(){
        boolean isOk = courierClient.create(courier)
                .statusCode(201).extract().path("ok");
        courierId = courierClient.login(CourierLogin.getLogin(courier))
                .statusCode(200)
                .extract()
                .path("id");
        String error = courierClient.create(courier)
                .statusCode(409).extract().path("message");

        assertTrue(isOk);
        assertNotEquals(0, courierId);
        assertEquals("Этот логин уже используется", error);
    }
    @Test

    public void createCourierWithoutLoginExpectError(){
        courier.setLogin("");
        String error = courierClient.create(courier)
                .statusCode(400).extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", error);

    }
    @Test

    public void createCourierWithoutPassExpectError(){
        courier.setPassword("");
        String error = courierClient.create(courier)
                .statusCode(400).extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", error);
    }

}