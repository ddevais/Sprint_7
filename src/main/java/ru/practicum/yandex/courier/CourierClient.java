package ru.practicum.yandex.courier;

import io.restassured.response.ValidatableResponse;
import ru.practicum.yandex.BaseClient;


public class CourierClient extends BaseClient {

    private static final String ROOT = "/courier";
    private static final String LOGIN = ROOT + "/login";
    private static final String COURIER = "/courier/{courierId}";

    public ValidatableResponse create(Courier courier) {
        return getSpec()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all()
                .assertThat();
    }
    public ValidatableResponse login(CourierLogin courierLogin){
        return getSpec()
                .body(courierLogin)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat();

    }

    public void delete(int courierId){
        getSpec()
                .pathParams("courierId", courierId)
                .when()
                .delete(COURIER)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");

    }

}
