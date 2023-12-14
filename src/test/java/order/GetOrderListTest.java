package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static constants.UrlAddresses.MAIN_URL;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = MAIN_URL;
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка, что в тело ответа возвращается список заказов")
    public void getOrderList() {
        Response response = OrderMethods.getOrderList();
        response.then().assertThat().statusCode(200)
                .and()
                .assertThat()
                .body("orders.id", notNullValue());
    }
}
