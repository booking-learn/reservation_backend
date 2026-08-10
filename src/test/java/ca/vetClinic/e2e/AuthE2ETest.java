package ca.vetClinic.e2e;

import ca.vetClinic.api.dto.request.LoginReq;
import ca.vetClinic.api.dto.request.RegisterReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import tools.jackson.databind.json.JsonMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthE2ETest extends BaseE2ETest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private JsonMapper jsonMapper;

	@Nested
	class Register {

		@BeforeEach
		void cleanUp() {
			jdbcTemplate.execute("DELETE FROM users");
			jdbcTemplate.execute("DELETE FROM accounts");
		}

		@Test
		void givenWhenValidRequest_thenSuccess() throws Exception {
			mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
					{
					  "email": "gontran@gmail.com",
					  "password": "string",
					  "firstName": "gontran",
					  "lastName": "s",
					  "phoneNumber": "83783692988"
					}
					""")).andExpect(status().is(201)).andExpect(jsonPath("$.accessToken").exists());
		}

		@Test
		void givenWhenInvalidEmail_thenBadRequestStatus() throws Exception {
			mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
					{
					  "email": "gontrangmail.com",
					  "password": "string",
					  "firstName": "gontran",
					  "lastName": "s",
					  "phoneNumber": "83783692988"
					}
					""")).andExpect(status().isBadRequest()).andExpect(jsonPath("$.accessToken").doesNotExist());
		}

		@Test
		void givenWhenInvalidPassword_thenBadRequestStatus() throws Exception {
			mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
					{
					  "email": "gontran@gmail.com",
					  "password": "",
					  "firstName": "gontran",
					  "lastName": "s",
					  "phoneNumber": "83783692988"
					}
					""")).andExpect(status().isBadRequest()).andExpect(jsonPath("$.accessToken").doesNotExist());
		}

		@Test
		void givenWhenInvalidFirstName_thenBadRequestStatus() throws Exception {
			mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
					{
					  "email": "gontran@gmail.com",
					  "password": "string",
					  "firstName": "",
					  "lastName": "s",
					  "phoneNumber": "83783692988"
					}
					""")).andExpect(status().isBadRequest()).andExpect(jsonPath("$.accessToken").doesNotExist());
		}

		@Test
		void givenWhenInvalidLastName_thenBadRequestStatus() throws Exception {
			mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
					{
					  "email": "gontran@gmail.com",
					  "password": "string",
					  "firstName": "gontran",
					  "lastName": "",
					  "phoneNumber": "83783692988"
					}
					""")).andExpect(status().isBadRequest()).andExpect(jsonPath("$.accessToken").doesNotExist());
		}

		@Test
		void givenWhenInvalidPhoneNumber_thenBadRequestStatus() throws Exception {
			mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
					{
					  "email": "gontran@gmail.com",
					  "password": "string",
					  "firstName": "gontran",
					  "lastName": "s",
					  "phoneNumber": "838ee369fej8"
					}
					""")).andExpect(status().isBadRequest()).andExpect(jsonPath("$.accessToken").doesNotExist());
		}
	}

	@Nested
	class Login {

		@BeforeEach
		void setUp() throws Exception {
			RegisterReq registerReq = new RegisterReq("test@email.com", "password123", "John", "Doe", "+14181234567");

			mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
					.content(jsonMapper.writeValueAsString(registerReq))).andExpect(status().is(201));
		}

		@Test
		void givenWhenValidCredentials_thenSuccess() throws Exception {
			LoginReq loginReq = new LoginReq("test@email.com", "password123");

			mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
					.content(jsonMapper.writeValueAsString(loginReq))).andExpect(status().isOk());
		}

		@Test
		void givenWhenInvalidEmail_thenBadRequestStatus() throws Exception {
			LoginReq loginReq = new LoginReq("testemail.com", "password123");

			mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
					.content(jsonMapper.writeValueAsString(loginReq))).andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.token").doesNotExist());
		}

		@Test
		void givenWhenInvalidPassword_thenBadRequestStatus() throws Exception {
			LoginReq loginReq = new LoginReq("test@email.com", "password13");

			mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
					.content(jsonMapper.writeValueAsString(loginReq))).andExpect(status().isUnauthorized())
					.andExpect(jsonPath("$.token").doesNotExist());
		}
	}
}