package courier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrderTest {
    private final OrdersGenerator ordersGenerator = new OrdersGenerator();
    private final CourierClient client = new CourierClient();
    private final CourierAssertions check = new CourierAssertions();


    @Test
    @DisplayName("Список заказов")
    public void order() { // проверка списка заказов
        var order = ordersGenerator.generic();
        ValidatableResponse creationResponse = client.orderList(order);
       check.createdOrderList(creationResponse);

    }
}
