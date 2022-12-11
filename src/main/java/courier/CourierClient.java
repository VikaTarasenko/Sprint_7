package courier;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient { // В Client выносить все, что дергает ручки апи
    private final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private final String ROOT = "/api/v1/courier";
    private final String ROOT1 = "/api/v1/orders";
    public ValidatableResponse create(Courier courier){ // вынесли повторяющуюся часть дергания апи отдельно
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all();

    }
    public ValidatableResponse login(Credentials creds) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(creds)
                .when()
                .post(ROOT + "/login")
                .then().log().all();

    }
    public ValidatableResponse delete(int courierId) {
        String json = String.format("{\"id\": \"%d\"}", courierId);
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(json)
                .when()
                .delete(ROOT + "/" + courierId)
                .then().log().all();

    }
    public ValidatableResponse createOrder(Order order){ // вынесли повторяющуюся часть дергания апи отдельно
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post(ROOT1)
                .then().log().all();

    }

}
