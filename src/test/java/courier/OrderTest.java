package courier;
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
    public void order() { // проверка списка заказов
        var order = ordersGenerator.generic();
        ValidatableResponse creationResponse = client.createOrder(order);

        given().log().all()
                .contentType(ContentType.JSON)
                .baseUri("https://qa-scooter.praktikum-services.ru")
                .body(order)
                .when()
                .get("/api/v1/orders")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("orders", notNullValue())
                .extract()
                .path("orders");
    }
}
