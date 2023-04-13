package liveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    Map<String, String> headers = new HashMap();

    String userResourcePath = "/api/users";

    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
//set headers
        headers.put("Content-Type", "application/json");
        //set body

        DslPart requestResponseBody = new PactDslJsonBody()
                .numberType("id")
                .stringType("firstName")
                .stringType("lastName")
                .stringType("email");

        //
        return builder.given("A request to create a user")
                .uponReceiving("A request to create a user")
                .method("POST")
                .headers(headers)
                .path(userResourcePath)
                .body(requestResponseBody)

                .willRespondWith()
                .status(201)
                .body(requestResponseBody)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = "UserProvider", port = "8282")
    public void consumerTest() {
        final String baseURI = "http://localhost:8282";

        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id", 99);
        reqBody.put("firstName", "vimala");
        reqBody.put("lastName", "narayanas");
        reqBody.put("email", "vimalanarayans@example.com");

        Response response = given().headers(headers).when().body(reqBody).post(baseURI + userResourcePath);
        response.then().statusCode(201);
    }
}