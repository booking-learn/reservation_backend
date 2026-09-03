package ca.vetClinic.e2e;

import ca.vetClinic.api.dto.response.AuthResponse;
import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.Pet;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.repository.AccountRepository;
import ca.vetClinic.domain.repository.PetRepository;
import ca.vetClinic.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.password.PasswordEncoder;
import tools.jackson.databind.json.JsonMapper;

import javax.sql.DataSource;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PetE2ETest extends BaseE2ETest {

	private static final String OWNER_EMAIL = "owner.pet@vetclinic.test";
	private static final String OWNER_PASSWORD = "OwnerTest123!";
	private static final String PET_NAME = "Milo";

	@Autowired
	private JsonMapper jsonMapper;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PetRepository petRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private String itAdminToken;
	private String ownerToken;
	private UUID petId;

	@BeforeEach
	void setUp() throws Exception {
		seedItAdmin();
		itAdminToken = login("it.admin@vetclinic.test", "ItAdmin123!");

		Account account = new Account(null, OWNER_EMAIL, passwordEncoder.encode(OWNER_PASSWORD), Role.USER);
		accountRepository.save(account);
		User owner = new User(null, account.getId(), "Jacob", "Tremblay", "4185551234", Instant.now());
		userRepository.save(owner);
		Pet pet = new Pet(null, owner.getId(), PET_NAME, "Cat", null, "MALE", LocalDate.now());
		petRepository.save(pet);
		petId = pet.getId();

		ownerToken = login(OWNER_EMAIL, OWNER_PASSWORD);
	}

	private void seedItAdmin() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("sql/CreateItAdmin.sql"));
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
		void givenOwnerToken_whenCreatePet_thenOk() throws Exception {
			mockMvc.perform(post("/pets").header("Authorization", "Bearer " + ownerToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "name": "Rex",
							  "species": "DOG",
							  "breed": "Labrador",
							  "gender": "MALE",
							  "birthDate": "2022-01-15"
							}
							""")).andExpect(status().isOk());
		}

		@Test
		void givenNoToken_whenCreatePet_thenUnauthorized() throws Exception {
			mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content("""
					{
					  "name": "Rex",
					  "species": "DOG",
					  "breed": "Labrador",
					  "gender": "MALE",
					  "birthDate": "2022-01-15"
					}
					""")).andExpect(status().isUnauthorized());
		}
	}

	@Nested
	class GetById {

		@Test
		void givenItAdmin_whenGetPetById_thenOk() throws Exception {
			mockMvc.perform(get("/pets/{petId}", petId).header("Authorization", "Bearer " + itAdminToken))
					.andExpect(status().isOk()).andExpect(jsonPath("$.name").value(PET_NAME));
		}

		@Test
		void givenOwnerToken_whenGetPetById_thenForbidden() throws Exception {
			mockMvc.perform(get("/pets/{petId}", petId).header("Authorization", "Bearer " + ownerToken))
					.andExpect(status().isForbidden());
		}

		@Test
		void givenNoToken_whenGetPetById_thenUnauthorized() throws Exception {
			mockMvc.perform(get("/pets/{petId}", petId)).andExpect(status().isUnauthorized());
		}
	}

	@Nested
	class GetMine {

		@Test
		void givenOwnerToken_whenGetPetsForCurrentUser_thenOk() throws Exception {
			mockMvc.perform(get("/pets/mine").header("Authorization", "Bearer " + ownerToken))
					.andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value(PET_NAME));
		}

		@Test
		void givenNoToken_whenGetPetsForCurrentUser_thenUnauthorized() throws Exception {
			mockMvc.perform(get("/pets/mine")).andExpect(status().isUnauthorized());
		}
	}

	@Nested
	class GetAll {

		@Test
		void givenItAdmin_whenGetAllPets_thenOk() throws Exception {
			mockMvc.perform(get("/pets").header("Authorization", "Bearer " + itAdminToken)).andExpect(status().isOk());
		}

		@Test
		void givenOwnerToken_whenGetAllPets_thenForbidden() throws Exception {
			mockMvc.perform(get("/pets").header("Authorization", "Bearer " + ownerToken))
					.andExpect(status().isForbidden());
		}

		@Test
		void givenNoToken_whenGetAllPets_thenUnauthorized() throws Exception {
			mockMvc.perform(get("/pets")).andExpect(status().isUnauthorized());
		}
	}

	@Nested
	class UpdateAsStaff {

		@Test
		void givenItAdmin_whenUpdatePetAsStaff_thenOk() throws Exception {
			mockMvc.perform(patch("/pets/{petId}", petId).header("Authorization", "Bearer " + itAdminToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "name": "Milo Modifie"
							}
							""")).andExpect(status().isOk());
		}

		@Test
		void givenOwnerToken_whenUpdatePetAsStaff_thenForbidden() throws Exception {
			mockMvc.perform(patch("/pets/{petId}", petId).header("Authorization", "Bearer " + ownerToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "name": "Milo Modifie"
							}
							""")).andExpect(status().isForbidden());
		}

		@Test
		void givenNoToken_whenUpdatePetAsStaff_thenUnauthorized() throws Exception {
			mockMvc.perform(patch("/pets/{petId}", petId).contentType(MediaType.APPLICATION_JSON).content("""
					{
					  "name": "Milo Modifie"
					}
					""")).andExpect(status().isUnauthorized());
		}
	}

	@Nested
	class UpdateAsOwner {

		@Test
		void givenOwnerToken_whenUpdatePetAsOwner_thenOk() throws Exception {
			mockMvc.perform(patch("/pets/{petId}/owner", petId).header("Authorization", "Bearer " + ownerToken)
					.contentType(MediaType.APPLICATION_JSON).content("""
							{
							  "name": "Milo Modifie"
							}
							""")).andExpect(status().isOk());
		}

		@Test
		void givenNoToken_whenUpdatePetAsOwner_thenUnauthorized() throws Exception {
			mockMvc.perform(patch("/pets/{petId}/owner", petId).contentType(MediaType.APPLICATION_JSON).content("""
					{
					  "name": "Milo Modifie"
					}
					""")).andExpect(status().isUnauthorized());
		}
	}

	@Nested
	class DeleteById {

		@Test
		void givenItAdmin_whenDeletePetById_thenOk() throws Exception {
			mockMvc.perform(delete("/pets/{petId}", petId).header("Authorization", "Bearer " + itAdminToken))
					.andExpect(status().isOk());
		}

		@Test
		void givenOwnerToken_whenDeletePetById_thenForbidden() throws Exception {
			mockMvc.perform(delete("/pets/{petId}", petId).header("Authorization", "Bearer " + ownerToken))
					.andExpect(status().isForbidden());
		}

		@Test
		void givenNoToken_whenDeletePetById_thenUnauthorized() throws Exception {
			mockMvc.perform(delete("/pets/{petId}", petId)).andExpect(status().isUnauthorized());
		}
	}

	@Nested
	class DeleteByOwner {

		@Test
		void givenOwnerToken_whenDeletePet_thenOk() throws Exception {
			mockMvc.perform(delete("/pets").header("Authorization", "Bearer " + ownerToken)).andExpect(status().isOk());
		}

		@Test
		void givenNoToken_whenDeletePet_thenUnauthorized() throws Exception {
			mockMvc.perform(delete("/pets")).andExpect(status().isUnauthorized());
		}
	}
}