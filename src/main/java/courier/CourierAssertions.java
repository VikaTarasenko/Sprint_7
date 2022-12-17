package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;

public class CourierAssertions { // В  Assertions выносим проверки
    @Step("Успешное создание курьера")
    public void createdSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(201)
                .body("ok", is(true));
    }
    @Step("Курьер логиниться успешно")
    public int loggedInSuccessfully(ValidatableResponse response) {
       return response.assertThat()
                .statusCode(200)
                .body("id", greaterThan(0))
                .extract()
                .path("id");

    }
    @Step("1 из полей не заполнено")
    public String creationFailed(ValidatableResponse response){
        return response.assertThat()
                .statusCode(400)
                .body("message", notNullValue())
                .extract()
                .path("message");

    }
    @Step("Удаление успешно")
    public void deletedSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("ok", is(true))
        ;
    }
    @Step("Логин уже используется")
    public void createdUnSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(409)
                .body("message",equalTo("Этот логин уже используется. Попробуйте другой."));

    }
    @Step("Не все полля заполнены")
    public void creationNotFullFields(ValidatableResponse response) {
        response.assertThat()
                .statusCode(409)
        ;
    }

    @Step("Создание заказа успешно")
    public void createdOrderSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(201)
                .body("track", greaterThan(0))
                .extract()
                .path("track");

    }
    @Step("Нет учетной записи")
    public void createdNonexistent(ValidatableResponse response) {
        response.assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

    }
    @Step("Список заказов получен")
    public void createdOrderList(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("orders", notNullValue())
                .extract()
                .path("orders");

    }

    }


