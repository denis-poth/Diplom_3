
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.given;

public class Api {
    public static RequestSpecification baseRequestSpec(){
        return new RequestSpecBuilder()
                .addHeader("Content-type", "application/json")
                .setBaseUri("https://stellarburgers.nomoreparties.site")
                .setRelaxedHTTPSValidation()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ErrorLoggingFilter())
                .build();
    }

    public static ValidatableResponse deleteUser(String accessToken) {
        return given().spec(baseRequestSpec())
                .header("Authorization", accessToken)
                .when()
                .delete("/api/auth/user")
                .then()
                .assertThat()
                .statusCode(202);
    }
    public static Response loginUser(User user) {
        return given().spec(baseRequestSpec())
                .body(user)
                .when()
                .post("/api/auth/login");
    }
}

