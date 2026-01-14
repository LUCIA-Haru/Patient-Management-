package integration_tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PatientIntegrationTest {
    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    public void shouldReturnPatientsWithValidToken(){
//        1.Arrange 2.act 3.assert

        String loginPayload = """
                {
                    "email":"testuser@test.com",
                    "password":"password123"
                }
                """;
//given() -> arrange , when() -> act , then() -> assert
        String token = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .log().all() //show my response format
                .extract()
                .jsonPath()
                .get("data.token");

        given()
                .header("Authorization","Bearer " + token)
                .when()
                .get("/api/patients")
                .then()
                .statusCode(200)
                .log().all();

    }
}
