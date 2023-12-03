package order;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static constants.UrlAddresses.ORDER;
import static io.restassured.RestAssured.given;

public class OrderMethods {
    @Step("Создание заказа")
    public static Response createOrder(Order order) {
        return given()
                .header("Content-Type", "application/json")
                .and()
                .body(order)
                .when()
                .post(ORDER);
    }

    @Step("Получение списка заказов")
    public static Response getOrderList() {
        return given()
                .header("Content-Type", "application/json")
                .when()
                .get(ORDER);
    }

    @Step("Отмена заказа")
    public static void cancelOrder(String orderId) {
        given()
                .delete(ORDER + "{orderId}", orderId);
    }



}
