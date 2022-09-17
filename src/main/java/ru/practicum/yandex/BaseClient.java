package ru.practicum.yandex;

import io.restassured.specification.RequestSpecification;
import ru.practicum.yandex.config.Config;

import static io.restassured.RestAssured.given;

public class BaseClient {
    protected RequestSpecification getSpec(){
        return given().log().all()
                .header("Content-type", "application/json")
                .baseUri(Config.URL);
    }
}
