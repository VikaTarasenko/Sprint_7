package courier;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;


public class OrdersGenerator {
    public Order generic(){
        return new Order ("Taras", "Tarasov", "Moskva, Federativni, 12/32", "5", "+7 800 000 05 05", 2, "2022-12-20","Комментарий здесь", List.of());

    }
    public Order random() {
        return new Order(RandomStringUtils.randomAlphanumeric(10), "Tarasov", "Moskva, Federativni, 12/32", "5", "+7 800 000 05 05", 2, "2022-12-20","Комментарий здесь", List.of()); //создаем рандом

    }
}
