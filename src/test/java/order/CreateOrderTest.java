package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static constants.UrlAddresses.MAIN_URL;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private static final String  firstName = "Иван";
    private static final String  lastName = "Иванов";
    private static final String  address = "Москва, ул. Ленина, д. 1";
    private static final String  metroStation = "Парк культуры";
    private static final String  phone = "9999999999";
    private static final int  rentTime = 1;
    private static final String  deliveryDate = "2021-08-31";
    private static final String  comment = "Тестовый заказ";
    private final String color;
    String track;

    @Before
    public void setUp() {
        RestAssured.baseURI = MAIN_URL;
    }

    public CreateOrderTest(String color) {
        this.color = color;
    }

    @Parameterized.Parameters (name = "colour = ''{0}''")
    public static Object[] getColour() {
        return new Object[][]{
                {"BLACK"},
                {"GREY"},
                {"BLACK GREY"},
                {""}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Заказ можно создать с указанием только одного цвета, обоих цветов, либо без их указания в принципе")
    public void createOrder() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, new String[]{color});
        Response response = OrderMethods.createOrder(order);
        track = response.then().extract().path("track").toString();
        response.then().assertThat().statusCode(201)
                .and()
                .assertThat()
                .body("track", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без указания параметра цвета")
    @Description("Заказ можно создать, если не передать параметр color")
    public void createOrderWithoutColor() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
        Response response = OrderMethods.createOrder(order);
        track = response.then().extract().path("track").toString();
        response.then().assertThat().statusCode(201)
                .and()
                .assertThat()
                .body("track", notNullValue());
    }

    @After
    public void cancelOrder() {
        OrderMethods.cancelOrder(track);
    }
}
