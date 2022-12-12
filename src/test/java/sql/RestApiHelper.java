package sql;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public class RestApiHelper {

    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static String PaymentPageForm (data.DataHelper.CardInfo CardInfo) {
        return given()
                .spec(requestSpec)
                .body(CardInfo)
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(200)
                .extract().response().asString();
    }

    public static String CreditRequestPageForm (data.DataHelper.CardInfo CardInfo) {
        return given()
                .spec(requestSpec)
                .body(CardInfo)
                .when()
                .post("/api/v1/credit")
                .then()
                .statusCode(200)
                .extract().response().asString();
    }

}