package ca.vetClinic.e2e;

import ca.vetClinic.api.dto.request.LoginReq;
import ca.vetClinic.api.dto.request.RegisterReq;
import ca.vetClinic.api.dto.response.AuthResponse;
import ca.vetClinic.api.dto.response.EmplCreatedResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import tools.jackson.databind.json.JsonMapper;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthE2ETest extends BaseE2ETest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private JsonMapper jsonMapper;
	@Autowired
	private DataSource dataSource;

	private String itAdminToken;
	private String employeeCreatedPassword;
	private String employeeCreatedEmail;

	private String NEW_PASSWORD = "querty";

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
					.andExpect(jsonPath("$.accessToken").doesNotExist());
		}

		@Test
		void givenWhenInvalidPassword_thenBadRequestStatus() throws Exception {
			LoginReq loginReq = new LoginReq("test@email.com", "password13");

			mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
					.content(jsonMapper.writeValueAsString(loginReq))).andExpect(status().isUnauthorized())
					.andExpect(jsonPath("$.accessToken").doesNotExist());
		}
	}

	private void seedDatabase() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("sql/CreateItAdmin.sql"));
		populator.addScript(new ClassPathResource("sql/CreateEmployee.sql"));
		populator.execute(dataSource);
	}

	private String login(String email, String password) throws Exception {
		String body = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content("""
				{
				  "email": "%s",
				  "password": "%s"
				}
				""".formatted(email, password))).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
		return jsonMapper.readValue(body, AuthResponse.class).accessToken();
	}

	@Nested
	class ChangePassword {
		@BeforeEach
		void setUp() throws Exception {
			seedDatabase();
			itAdminToken = login("it.admin@vetclinic.test", "ItAdmin123!");

			employeeCreatedEmail = "jo.mabiala@vetClinic.ca";
			String body = mockMvc
					.perform(post("/employees").header("Authorization", "Bearer " + itAdminToken)
							.contentType(MediaType.APPLICATION_JSON).content("""
									                   {
									                     "firstName": "jo",
									"lastName": "mabiala",
									"phoneNumber": "83783692988",
									"role": "VET_TECH"
									                   }
									                   """))
					.andExpect(status().is(201)).andReturn().getResponse().getContentAsString();
			employeeCreatedPassword = String.valueOf(jsonMapper.readValue(body, EmplCreatedResponse.class).password());
		}

		@AfterEach
		void tearDown() {
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
			populator.addScript(new ClassPathResource("sql/CleanupEmployee.sql"));
			populator.execute(dataSource);
		}

		@Test
		void givenEmployeeFirstLoginWithoutChangingPassword_thenForbidden() throws Exception {
			mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content("""
					{
					    "email": "%s",
					    "password": "%s"
					}
					""".formatted(employeeCreatedEmail, employeeCreatedPassword))).andExpect(status().isForbidden());
		}

		@Nested
		class PasswordChanged {
			@BeforeEach
			void setup() throws Exception {
				mockMvc.perform(patch("/auth/password").contentType(MediaType.APPLICATION_JSON).content("""
						{
						    "email": "%s",
						    "oldPassword": "%s",
						    "newPassword": "%s"
						}
						""".formatted(employeeCreatedEmail, employeeCreatedPassword, NEW_PASSWORD)))
						.andExpect(status().isNoContent());
			}

			@Test
			void givenPasswordChanged_thenLoginSuccess() throws Exception {
				LoginReq loginReq = new LoginReq(employeeCreatedEmail, NEW_PASSWORD);

				mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
						.content(jsonMapper.writeValueAsString(loginReq))).andExpect(status().isOk());
			}
		}
	}
}