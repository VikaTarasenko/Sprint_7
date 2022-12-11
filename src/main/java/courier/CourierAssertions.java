package courier;

import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.*;

public class CourierAssertions { // В  Assertions выносим проверки
    public void createdSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(201)
                .body("ok", is(true));
    }
    public int loggedInSuccessfully(ValidatableResponse response) {
       return response.assertThat()
                .statusCode(200)
                .body("id", greaterThan(0))
                .extract()
                .path("id");

    }
    public String creationFailed(ValidatableResponse response){
        return response.assertThat()
                .statusCode(400)
                .body("message", notNullValue())
                .extract()
                .path("message");
    }
    public void deletedSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("ok", is(true))
        ;
    }
    public void createdUnSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(409)
                .body("message",equalTo("Этот логин уже используется. Попробуйте другой."));

    }
    public void creationNotFullFields(ValidatableResponse response) {
        response.assertThat()
                .statusCode(409)
        ;
    }
    public ValidatableResponse loggedInUnSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(400)
                .body("message",equalTo("Недостаточно данных для входа"));


    }
    public void createdOrderSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(201)
                .body("track", greaterThan(0))
                .extract()
                .path("track");

    }
}
