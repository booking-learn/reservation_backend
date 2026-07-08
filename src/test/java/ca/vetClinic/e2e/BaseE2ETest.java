package ca.vetClinic.e2e;

import ca.vetClinic.base.AbstractContainerBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@Sql(scripts = "/sql/CleanUp.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseE2ETest extends AbstractContainerBase {

	@Autowired
	protected MockMvc mockMvc;

}