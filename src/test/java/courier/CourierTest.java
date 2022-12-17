package courier;

import io.qameta.allure.junit4.DisplayName;
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
    @DisplayName("Создание курьера")
    public void courier() {
        var courier = courierGenerator.random();
        ValidatableResponse creationResponse = client.create(courier);
        check.createdSuccessfully(creationResponse);

        Credentials creds = Credentials.from(courier); //логинимся
        ValidatableResponse loginResponse = client.login(creds);
        int id = check.loggedInSuccessfully(loginResponse);

        assert id > 100; //это дополнительная проверка на случай, если нужно убедиться, что значение  id больше 100
    }

    @Test
    @DisplayName("Пароль не введен/заполнено 1 поле")
    public void loginFails() { // негативный тест на проверку, что пароль не введен, проверка заполнения 1 поля
        var courier = courierGenerator.generic();
        courier.setPassword(null);
        ValidatableResponse loginResponse = client.create(courier);
        String message = check.creationFailed(loginResponse);
        assert !message.isBlank();
    }

    @Test
    @DisplayName("Создание 2х одинаковых куреров")
    public void checkSameCourier() { // проверяем создание 2 одинаковых курьеров
        var courier = courierGenerator.generic();
        ValidatableResponse creationResponse = client.create(courier);
        check.createdUnSuccessfully(creationResponse);
    }

    @Test
    @DisplayName("Заполнение всех полей")
    public void checkNotFullField() { // проверка на заполнение всех полей
        var courier = new Courier(" ", " ", " ");
        ValidatableResponse creationResponse = client.create(courier);
        check.creationNotFullFields(creationResponse); //почему-то ошибка "этот логин уже используется" вместо "Недостаточно данных для создания учетной записи"
    }

    @Test
    @DisplayName("Авторизация под несуществуюшим пользователем")
    public void checkNonexistentCred() { // проверка авторизации под несуществующим пользователем, 2 поля неверные
        var courier = courierGenerator.random(); // генерируем рандомного курьера
        ValidatableResponse loginResponse = client.login(Credentials.from(courier));
        check.createdNonexistent(loginResponse);

    }


}
