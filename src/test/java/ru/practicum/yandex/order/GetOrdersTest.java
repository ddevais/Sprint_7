package ru.practicum.yandex.order;
import io.restassured.response.Response;
import org.junit.Test;
import ru.practicum.yandex.config.Config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;


public class GetOrdersTest {


    @Test

    public void getAllOrders(){
        Response response = given().log().all()
                .baseUri(Config.URL)
                .get("/orders");
        response.then()
                .statusCode(200)
                .assertThat().body("orders", notNullValue());

    }

}