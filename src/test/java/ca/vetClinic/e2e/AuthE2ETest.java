package ca.vetClinic.e2e;

import ca.vetClinic.api.dto.request.LoginRequest;
import ca.vetClinic.api.dto.request.RegisterRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class AuthE2ETest extends BaseE2ETest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Nested
	class register {
		@BeforeEach
		void cleanUp() {
			jdbcTemplate.execute("DELETE FROM users");
			jdbcTemplate.execute("DELETE FROM accounts");
		}
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
		@Test
		void givenWhenInvalidEmail_thenBadRequestStatus() {
			given().contentType(ContentType.JSON).body("""
					  {
					  "email": "gontrangmail.com",
					  "password": "string",
					  "firstName": "gontran",
					  "lastName": "s",
					  "phoneNumber": "83783692988"
					}
					""").when().post("/auth/register").then().statusCode(400).body("accessToken", nullValue());
		}
		@Test
		void givenWhenInvalidPassword_thenBadRequestStatus() {
			given().contentType(ContentType.JSON).body("""
					  {
					  "email": "gontran@gmail.com",
					  "password": "",
					  "firstName": "gontran",
					  "lastName": "s",
					  "phoneNumber": "83783692988"
					}
					""").when().post("/auth/register").then().statusCode(400).body("accessToken", nullValue());
		}
		@Test
		void givenWhenInvalidFirstName_thenBadRequestStatus() {
			given().contentType(ContentType.JSON).body("""
					  {
					  "email": "gontran@gmail.com",
					  "password": "string",
					  "firstName": "",
					  "lastName": "s",
					  "phoneNumber": "83783692988"
					}
					""").when().post("/auth/register").then().statusCode(400).body("accessToken", nullValue());
		}
		@Test
		void givenWhenInvalidLastName_thenBadRequestStatus() {
			given().contentType(ContentType.JSON).body("""
					  {
					  "email": "gontran@gmail.com",
					  "password": "string",
					  "firstName": "gontran",
					  "lastName": "",
					  "phoneNumber": "83783692988"
					}
					""").when().post("/auth/register").then().statusCode(400).body("accessToken", nullValue());
		}
		@Test
		void givenWhenInvalidPhoneNumber_thenBadRequestStatus() {
			given().contentType(ContentType.JSON).body("""
					  {
					  "email": "gontran@gmail.com",
					  "password": "string",
					  "firstName": "gontran",
					  "lastName": "s",
					  "phoneNumber": "838ee369fej8"
					}
					""").when().post("/auth/register").then().statusCode(400).body("accessToken", nullValue());
		}
	}
	@Nested
	class login {
		@BeforeEach
		void setUp() {
			RegisterRequest registerRequest = new RegisterRequest("test@email.com", "password123", "John", "Doe",
					"+14181234567");

			given().contentType(ContentType.JSON).body(registerRequest).post("/auth/register").then().statusCode(201);
		}
		@Test
		void givenWhenValidCredentials_thenSuccess() {
			LoginRequest loginRequest = new LoginRequest("test@email.com", "password123");

			given().contentType(ContentType.JSON).body(loginRequest).post("/auth/login").then().statusCode(200);
		}
		@Test
		void givenWhenInvalidEmail_thenBadRequestStatus() {
			LoginRequest loginRequest = new LoginRequest("testemail.com", "password123");

			given().contentType(ContentType.JSON).body(loginRequest).post("/auth/login").then().statusCode(400)
					.body("token", nullValue());
		}
		@Test
		void givenWhenInvalidPassword_thenBadRequestStatus() {
			LoginRequest loginRequest = new LoginRequest("test@email.com", "password13");

			given().contentType(ContentType.JSON).body(loginRequest).post("/auth/login").then().statusCode(401)
					.body("token", nullValue());
		}

	}
}
