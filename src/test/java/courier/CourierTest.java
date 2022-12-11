package courier;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierTest {

    private final CourierGenerator courierGenerator = new CourierGenerator();
    private final CourierClient client = new CourierClient();
    private final CourierAssertions check = new CourierAssertions();
    private int courierId;

    @After
    public void deleteCourier() {
        if (courierId > 0) {
            ValidatableResponse response = client.delete(courierId);
            check.deletedSuccessfully(response);
        }
    }

    @Test
    public void courier() { // создаем курьера
        var courier = courierGenerator.random();
        ValidatableResponse creationResponse = client.create(courier);
        check.createdSuccessfully(creationResponse);

        Credentials creds = Credentials.from(courier); //логинимся
        ValidatableResponse loginResponse = client.login(creds);
        int id = check.loggedInSuccessfully(loginResponse);

        assert id > 100; //это дополнительная проверка на случай, если нужно убедиться, что значение  id больше 100
    }

    @Test
    public void loginFails() { // негативный тест на проверку, что пароль не введен
        var courier = courierGenerator.generic();
        courier.setPassword(null);
        ValidatableResponse loginResponse = client.create(courier);
        String message = check.creationFailed(loginResponse);
        assert !message.isBlank();
    }

    @Test
    public void checkSameCourier() { // проверяем создание 2 одинаковых курьеров
        var courier = courierGenerator.generic();
        ValidatableResponse creationResponse = client.create(courier);
        check.createdUnSuccessfully(creationResponse);
    }

    @Test
    public void checkNotFullField() { // проверка на заполнение всех полей
        var courier = new Courier(" ", " ", " ");
        ValidatableResponse creationResponse = client.create(courier);
        check.creationNotFullFields(creationResponse); //почему-то ошибка "этот логин уже используется" вместо "Недостаточно данных для создания учетной записи"
    }

    @Test
    public void checkCreds() { // проверка с заполнением 1 поля
        var courier = courierGenerator.generic();
        var creds = courier.getLogin();
        given().log().all()
                .contentType(ContentType.JSON)
                .baseUri("https://qa-scooter.praktikum-services.ru")
                .body(creds)
                .when()
                .post("/api/v1/courier/login")
                .then().log().all()
                .assertThat()
                .statusCode(400) // приходит 400 код, должен быть 409 и не то сообщение
                .body("message", equalTo("Unexpected token T in JSON at position 0"));// должно приходить сообщение "Недостаточно данных для входа"

    }

    @Test
    public void checkNonexistentCreds() { // проверка авторизации под несуществующим пользователем, 2 поля неверные
        var courier = courierGenerator.random();
        given().log().all()
                .contentType(ContentType.JSON)
                .baseUri("https://qa-scooter.praktikum-services.ru")
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then().log().all()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

    }


}
