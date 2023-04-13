package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity1 {

    final static String baseURI = "https://petstore.swagger.io/v2/pet";

    @Test(priority = 1)
    public void addPet() {
        String reqBody = "{" + "\"id\": 77232," + "\"name\": \"Riley\"," + " \"status\": \"alive\"" + "}";
        Response response = given().contentType(ContentType.JSON)
                .body(reqBody)
                .when().post(baseURI);
        response.then().body("id", equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
    }

    @Test(priority = 2)
    public void getPetdetails() {
        Response response = given().contentType(ContentType.JSON)
                .when().pathParam("petId", "77232")
                .get(baseURI + "/{petId}");

        response.then().body("id", equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));

    }

    @Test(priority = 3)
    public void deletePet() {
        Response response = given().contentType(ContentType.JSON)
                .when().pathParam("petId", "77232")
                .delete(baseURI + "/{petId}");

        response.then().statusCode(200);
        response.then().body("message", equalTo("77232"));

    }
}