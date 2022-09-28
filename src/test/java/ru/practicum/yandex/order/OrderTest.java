package ru.practicum.yandex.order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest {

    private final String[] color;
    private int trackId;
    Order order;
    OrderClient orderClient;

public OrderTest(String[] color){

    this.color = color;
}
@Parameterized.Parameters
        public static Collection<Object[]> getOrderCred(){
    return Arrays.asList(new Object[][]{
            {new String[]{"BLACK"}},
            {new String[]{"GREY"}},
            {new String[]{""}},
            {new String[]{"BLACK", "GREY"}},

    });

    }



    @Before
    public void setup(){
order = Order.getRandomOrder();
orderClient = new OrderClient();

    }
    @After
    public void teardown(){

boolean isOk = orderClient.cancel(trackId).statusCode(200).extract().path("ok");
assertTrue(isOk);
    }

    @Test

    public void chooseScooterColorInOrder(){
order.setColor(color);
trackId = orderClient.create(order)
        .statusCode(201)
        .extract()
        .path("track");
assertNotEquals(0, trackId);
    }

}