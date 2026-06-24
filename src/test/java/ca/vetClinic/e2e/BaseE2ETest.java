package ca.vetClinic.e2e;

import ca.vetClinic.base.AbstractContainerBase;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/sql/CleanUp.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseE2ETest extends AbstractContainerBase {

	@LocalServerPort
	protected int port;

	@BeforeEach
	@Sql(scripts = "/sql/CleanUp.sql")
	public void setup() {
		RestAssured.port = port;
	}
}
