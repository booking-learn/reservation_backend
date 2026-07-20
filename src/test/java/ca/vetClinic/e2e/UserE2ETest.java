package ca.vetClinic.e2e;

import ca.vetClinic.api.dto.response.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import tools.jackson.databind.json.JsonMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserE2ETest extends BaseE2ETest {

	@Autowired
	private JsonMapper jsonMapper;

	private String accessToken;

	@BeforeEach
	void setUp() throws Exception {
		String responseBody = mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
				{
				  "email": "test@gmail.com",
				  "password": "string",
				  "firstName": "Test",
				  "lastName": "User",
				  "phoneNumber": "83783692988"
				}
				""")).andExpect(status().is(201)).andReturn().getResponse().getContentAsString();

		AuthResponse response = jsonMapper.readValue(responseBody, AuthResponse.class);
		accessToken = response.accessToken();
	}

	@Nested
	class Update {

		@Test
		void givenValidRequest_whenUpdate_thenNoContent() throws Exception {
			mockMvc.perform(put("/user").header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "firstName": "Gontran",
							  "lastName": "Dupont",
							  "phoneNumber": "83783692988"
							}
							""")).andExpect(status().isNoContent());
		}

		@Test
		void givenNoToken_whenUpdate_thenUnauthorized() throws Exception {
			mockMvc.perform(put("/user").contentType(MediaType.APPLICATION_JSON).content("""
					{
					  "firstName": "Gontran",
					  "lastName": "Dupont",
					  "phoneNumber": "83783692988"
					}
					""")).andExpect(status().isUnauthorized());
		}

		@Test
		void givenInvalidFirstName_whenUpdate_thenBadRequestStatus() throws Exception {
			mockMvc.perform(put("/user").header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "firstName": "",
							  "lastName": "Dupont",
							  "phoneNumber": "83783692988"
							}
							""")).andExpect(status().isBadRequest());
		}

		@Test
		void givenInvalidPhoneNumber_whenUpdate_thenBadRequestStatus() throws Exception {
			mockMvc.perform(put("/user").header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "firstName": "Gontran",
							  "lastName": "Dupont",
							  "phoneNumber": "838ee369fej8"
							}
							""")).andExpect(status().isBadRequest());
		}
	}

	@Nested
	class UpdateEmail {

		@Test
		void givenValidOldEmail_whenUpdateEmail_thenNoContent() throws Exception {
			mockMvc.perform(patch("/user/email").header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "oldEmail": "test@gmail.com",
							  "newEmail": "nouveau@gmail.com"
							}
							""")).andExpect(status().isNoContent());
		}

		@Test
		void givenWrongOldEmail_whenUpdateEmail_thenUnauthorized() throws Exception {
			mockMvc.perform(patch("/user/email").header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "oldEmail": "mauvais@gmail.com",
							  "newEmail": "nouveau@gmail.com"
							}
							""")).andExpect(status().isUnauthorized());
		}

		@Test
		void givenNoToken_whenUpdateEmail_thenUnauthorized() throws Exception {
			mockMvc.perform(patch("/user/email").contentType(MediaType.APPLICATION_JSON).content("""
					{
					  "oldEmail": "test@gmail.com",
					  "newEmail": "nouveau@gmail.com"
					}
					""")).andExpect(status().isUnauthorized());
		}

		@Test
		void givenInvalidNewEmail_whenUpdateEmail_thenBadRequestStatus() throws Exception {
			mockMvc.perform(patch("/user/email").header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "oldEmail": "test@gmail.com",
							  "newEmail": "nouveaugmail.com"
							}
							""")).andExpect(status().isBadRequest());
		}
	}

	@Nested
	class UpdatePassword {

		@Test
		void givenValidOldPassword_whenUpdatePassword_thenNoContent() throws Exception {
			mockMvc.perform(patch("/user/password").header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "oldPassword": "string",
							  "newPassword": "nouveauMotDePasse123"
							}
							""")).andExpect(status().isNoContent());
		}

		@Test
		void givenWrongOldPassword_whenUpdatePassword_thenUnauthorized() throws Exception {
			mockMvc.perform(patch("/user/password").header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "oldPassword": "mauvaisMotDePasse",
							  "newPassword": "nouveauMotDePasse123"
							}
							""")).andExpect(status().isUnauthorized());
		}

		@Test
		void givenNoToken_whenUpdatePassword_thenUnauthorized() throws Exception {
			mockMvc.perform(patch("/user/password").contentType(MediaType.APPLICATION_JSON).content("""
					{
					  "oldPassword": "string",
					  "newPassword": "nouveauMotDePasse123"
					}
					""")).andExpect(status().isUnauthorized());
		}

		@Test
		void givenInvalidNewPassword_whenUpdatePassword_thenBadRequestStatus() throws Exception {
			mockMvc.perform(patch("/user/password").header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "oldPassword": "string",
							  "newPassword": ""
							}
							""")).andExpect(status().isBadRequest());
		}
	}
	@Nested
	class Get {
		@Test
		void givenWhenValidUpdateUser_thenOkAndReturnBody() throws Exception {
			mockMvc.perform(get("/user/me").header("Authorization", "Bearer " + accessToken)).andExpect(status().isOk())
					.andExpect(jsonPath("$.firstName").value("Test")).andExpect(jsonPath("$.lastName").value("User"))
					.andExpect(jsonPath("$.phoneNumber").value("83783692988"));

		}
	}
}