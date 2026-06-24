package ca.vetClinic.e2e;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthE2ETest extends BaseE2ETest {

	@Nested
	class register {
		@Test
		void givenWhenValidRequest_thenSuccess() {
			given().contentType(ContentType.JSON).body("""
					  {
					  "email": "gontran@gmail.com",
					  "password": "string",
					  "firstName": "gontran",
					  "lastName": "s",
					  "phoneNumber": "83783692988"
					}
					""").when().post("/auth/register").then().statusCode(201).body("accessToken", notNullValue());
		}
	}
	@Nested
	class login {
	}
}
