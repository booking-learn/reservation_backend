package ca.vetClinic.e2e;

import ca.vetClinic.api.dto.response.AuthResponse;
import ca.vetClinic.api.dto.response.EmplCreatedResponse;
import ca.vetClinic.api.dto.response.EmployeeResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import tools.jackson.databind.json.JsonMapper;

import javax.sql.DataSource;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeTest extends BaseE2ETest {

	private static final UUID SEEDED_EMPLOYEE_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");
	private static final String SEEDED_EMPLOYEE_EMAIL = "it.employee@vetclinic.test";
	private static final String SEEDED_EMPLOYEE_PASSWORD = "EmployeeTest123!";

	@Autowired
	private JsonMapper jsonMapper;

	@Autowired
	private DataSource dataSource;

	private String itAdminToken;
	private String nonAdminToken;
	private String employeeToken;
	private String employeeCreatedPassword;
	private String employeeCreatedEmail;

	@BeforeEach
	void setUp() throws Exception {
		seedDatabase();

		itAdminToken = login("it.admin@vetclinic.test", "ItAdmin123!");

		String userBody = mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
				{
				  "email": "nonadmin@gmail.com",
				  "password": "string",
				  "firstName": "Non",
				  "lastName": "Admin",
				  "phoneNumber": "83783692988"
				}
				""")).andExpect(status().is(201)).andReturn().getResponse().getContentAsString();
		nonAdminToken = jsonMapper.readValue(userBody, AuthResponse.class).accessToken();

		employeeToken = login(SEEDED_EMPLOYEE_EMAIL, SEEDED_EMPLOYEE_PASSWORD);
	}

	@AfterEach
	void tearDown() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("sql/CleanupEmployee.sql"));
		populator.execute(dataSource);
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
	class Create {

		@Test
		void givenItAdmin_whenCreateEmployee_thenCreated() throws Exception {
			mockMvc.perform(post("/employee").header("Authorization", "Bearer " + itAdminToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "firstName": "Gontran",
							  "lastName": "Dupont",
							  "phoneNumber": "83783692988",
							  "role": "VETERINARIAN"
							}
							""")).andExpect(status().isCreated());
		}
		@Test
		void givenItAdmin_whenCreateEmployee_thenReturnPassword() throws Exception {
			mockMvc.perform(post("/employee").header("Authorization", "Bearer " + itAdminToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "firstName": "Gontran",
							  "lastName": "Dupont",
							  "phoneNumber": "83783692988",
							  "role": "VETERINARIAN"
							}
							""")).andExpect(status().isCreated()).andExpect(jsonPath("$.password").exists())
					.andExpect(jsonPath("$.password").isNotEmpty());
		}

		@Test
		void givenNonAdmin_whenCreateEmployee_thenForbidden() throws Exception {
			mockMvc.perform(post("/employee").header("Authorization", "Bearer " + nonAdminToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "firstName": "Gontran",
							  "lastName": "Dupont",
							  "phoneNumber": "83783692988",
							  "role": "VETERINARIAN"
							}
							""")).andExpect(status().isForbidden());
		}

		@Test
		void givenNoToken_whenCreateEmployee_thenUnauthorized() throws Exception {
			mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_JSON).content("""
					{
					  "firstName": "Gontran",
					  "lastName": "Dupont",
					  "phoneNumber": "83783692988",
					  "role": "VETERINARIAN"
					}
					""")).andExpect(status().isUnauthorized());
		}

		@Test
		void givenInvalidFirstName_whenCreateEmployee_thenBadRequest() throws Exception {
			mockMvc.perform(post("/employee").header("Authorization", "Bearer " + itAdminToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "firstName": "",
							  "lastName": "Dupont",
							  "phoneNumber": "83783692988",
							  "role": "VETERINARIAN"
							}
							""")).andExpect(status().isBadRequest());
		}
	}
	@Nested
	class MustChangePassword {
		@BeforeEach
		void setup() throws Exception {
			employeeCreatedEmail = "jo.mabiala@vetClinic.ca";
			String body = mockMvc
					.perform(post("/employee").header("Authorization", "Bearer " + itAdminToken)
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
		@Test
		void givenEmployeeFirstLoginWithoutChangingPassword_thenForbidden() throws Exception {
			mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content("""
					{
					    "email": "%s",
					    "password": "%s"
					}
					""".formatted(employeeCreatedEmail, employeeCreatedPassword))).andExpect(status().isForbidden());
		}
	}
	@Nested
	class GetMe {

		@Test
		void givenEmployeeToken_whenGetMe_thenOkAndReturnBody() throws Exception {
			mockMvc.perform(get("/employee/me").header("Authorization", "Bearer " + employeeToken))
					.andExpect(status().isOk()).andExpect(jsonPath("$.firstName").value("Jean"))
					.andExpect(jsonPath("$.lastName").value("Testeur"))
					.andExpect(jsonPath("$.phoneNumber").value("5145550000"));
		}

		@Test
		void givenNoToken_whenGetMe_thenUnauthorized() throws Exception {
			mockMvc.perform(get("/employee/me")).andExpect(status().isUnauthorized());
		}
	}

	@Nested
	class GetById {

		@Test
		void givenItAdmin_whenGetEmployeeById_thenOk() throws Exception {
			mockMvc.perform(get("/employee/{id}", SEEDED_EMPLOYEE_ID).header("Authorization", "Bearer " + itAdminToken))
					.andExpect(status().isOk()).andExpect(jsonPath("$.firstName").value("Jean"));
		}

		@Test
		void givenNonAdmin_whenGetEmployeeById_thenForbidden() throws Exception {
			mockMvc.perform(
					get("/employee/{id}", SEEDED_EMPLOYEE_ID).header("Authorization", "Bearer " + nonAdminToken))
					.andExpect(status().isForbidden());
		}

		@Test
		void givenNoToken_whenGetEmployeeById_thenUnauthorized() throws Exception {
			mockMvc.perform(get("/employee/{id}", SEEDED_EMPLOYEE_ID)).andExpect(status().isUnauthorized());
		}
	}

	@Nested
	class GetAll {

		@Test
		void givenItAdmin_whenGetAllEmployees_thenOk() throws Exception {
			mockMvc.perform(get("/employee").header("Authorization", "Bearer " + itAdminToken))
					.andExpect(status().isOk());
		}

		@Test
		void givenNonAdmin_whenGetAllEmployees_thenForbidden() throws Exception {
			mockMvc.perform(get("/employee").header("Authorization", "Bearer " + nonAdminToken))
					.andExpect(status().isForbidden());
		}
	}

	@Nested
	class Update {

		@Test
		void givenEmployeeToken_whenUpdateEmployee_thenNoContent() throws Exception {
			mockMvc.perform(put("/employee").header("Authorization", "Bearer " + employeeToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "firstName": "Jean",
							  "lastName": "Modifie",
							  "phoneNumber": "5145550001"
							}
							""")).andExpect(status().isNoContent());
		}

		@Test
		void givenNoToken_whenUpdateEmployee_thenUnauthorized() throws Exception {
			mockMvc.perform(put("/employee").contentType(MediaType.APPLICATION_JSON).content("""
					{
					  "firstName": "Jean",
					  "lastName": "Modifie",
					  "phoneNumber": "5145550001"
					}
					""")).andExpect(status().isUnauthorized());
		}
	}

	@Nested
	class UpdatePassword {

		@Test
		void givenValidOldPassword_whenUpdatePassword_thenNoContent() throws Exception {
			mockMvc.perform(patch("/employee/password").header("Authorization", "Bearer " + employeeToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "oldPassword": "%s",
							  "newPassword": "NouveauMotDePasse456!"
							}
							""".formatted(SEEDED_EMPLOYEE_PASSWORD))).andExpect(status().isNoContent());
		}

		@Test
		void givenWrongOldPassword_whenUpdatePassword_thenUnauthorized() throws Exception {
			mockMvc.perform(patch("/employee/password").header("Authorization", "Bearer " + employeeToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "oldPassword": "mauvaisMotDePasse",
							  "newPassword": "NouveauMotDePasse456!"
							}
							""")).andExpect(status().isUnauthorized());
		}

		@Test
		void givenNoToken_whenUpdatePassword_thenUnauthorized() throws Exception {
			mockMvc.perform(patch("/employee/password").contentType(MediaType.APPLICATION_JSON).content("""
					{
					  "oldPassword": "%s",
					  "newPassword": "NouveauMotDePasse456!"
					}
					""".formatted(SEEDED_EMPLOYEE_PASSWORD))).andExpect(status().isUnauthorized());
		}
	}

	@Nested
	class Delete {

		@Test
		void givenItAdmin_whenDeleteEmployee_thenNoContent() throws Exception {
			mockMvc.perform(
					delete("/employee/{id}", SEEDED_EMPLOYEE_ID).header("Authorization", "Bearer " + itAdminToken))
					.andExpect(status().isNoContent());
		}

		@Test
		void givenNonAdmin_whenDeleteEmployee_thenForbidden() throws Exception {
			mockMvc.perform(
					delete("/employee/{id}", SEEDED_EMPLOYEE_ID).header("Authorization", "Bearer " + nonAdminToken))
					.andExpect(status().isForbidden());
		}

		@Test
		void givenNoToken_whenDeleteEmployee_thenUnauthorized() throws Exception {
			mockMvc.perform(delete("/employee/{id}", SEEDED_EMPLOYEE_ID)).andExpect(status().isUnauthorized());
		}
	}
}