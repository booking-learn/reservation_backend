package ca.vetClinic.e2e;

import ca.vetClinic.api.dto.response.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import tools.jackson.databind.json.JsonMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileE2ETest extends BaseE2ETest {

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

	@Test
	void givenValidToken_whenGetProfile_thenSuccess() throws Exception {
		mockMvc.perform(get("/profile").header("Authorization", "Bearer " + accessToken)).andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value("test@gmail.com"));
	}
}