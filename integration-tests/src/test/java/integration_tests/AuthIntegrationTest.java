package integration_tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest {
    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    public void shouldReturnOKWithValidToken(){
//        1.Arrange 2.act 3.assert

        String loginPayload = """
                {
                    "email":"testuser@test.com",
                    "password":"password123"
                }
                """;
//given() -> arrange , when() -> act , then() -> assert
        Response response = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("data.token", notNullValue())
                .log().all() //show my response format
                .extract()
                .response();

        System.out.println("Generated Token:" +
                response.jsonPath().getString("data.token"));
    }

    @Test
    public void shouldReturnUnauthorizedOnInvalidLogin(){
//        1.Arrange 2.act 3.assert

        String loginPayload = """
                {
                    "email":"invaliduser@test.com",
                    "password":"wrongpassword"
                }
                """;
//given() -> arrange , when() -> act , then() -> assert
         given()
                 .log().all()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                 .log().all()
                .statusCode(401);//show my response format


    }
}
