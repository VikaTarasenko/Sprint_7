package courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {


    public Courier generic(){
        return new Courier("Taras", "Pas@234566g", "Tarasov");
    }
    public Courier random() {
        return new Courier(RandomStringUtils.randomAlphanumeric(10), "Pas@234566g", "Tarasov"); //создаем рандомный логин курьера, таким же принципом можно создать рандомный пароли и фамилию

    }
}
