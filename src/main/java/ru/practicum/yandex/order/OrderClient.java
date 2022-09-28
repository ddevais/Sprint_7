package ru.practicum.yandex.order;

import io.restassured.response.ValidatableResponse;
import ru.practicum.yandex.BaseClient;


public class OrderClient extends BaseClient {
    private static final String ROOT = "/orders";
    private static final String CANCEL = ROOT + "/cancel";

    public ValidatableResponse create(Order order){
        return getSpec()
                .body(order)
                .when()
                .post(ROOT)
                .then().log().all()
                .assertThat();
    }
    public ValidatableResponse cancel(int trackId){
        return getSpec()
                .queryParam("track", trackId)
                .when()
                .put(CANCEL)
                .then().log().all()
                .assertThat();
    }
}
