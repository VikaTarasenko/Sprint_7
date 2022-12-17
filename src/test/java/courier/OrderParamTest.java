package courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)
public class OrderParamTest {
    private final CourierClient client = new CourierClient();
    private final CourierAssertions check = new CourierAssertions();
    private int track;

    private List<String> color;
    public OrderParamTest(List<String> color) {
        this.color = color;
    }
    @Parameterized.Parameters
    public static Object[][] dataGen() {
        return new Object[][] {
                {List.of("GREY")},
                {List.of("BLACK")},
                {List.of("GREY", "BLACK")},
        };
    }
    @Test
    @DisplayName("Создание заказа, разные цвета")
    public void order() { // создаем заказ
        Order order = new Order("Taras", "Tarasov", "Moskva, Federativni, 12/32", "5", "+7 800 000 05 05", 2, "2022-12-20", "Комментарий здесь", color);
        ValidatableResponse creationResponse = client.createOrder(order);
        check.createdOrderSuccessfully(creationResponse);

    }
}
