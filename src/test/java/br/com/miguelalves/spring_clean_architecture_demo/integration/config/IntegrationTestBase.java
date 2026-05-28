package br.com.miguelalves.spring_clean_architecture_demo.integration.config;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import br.com.miguelalves.spring_clean_architecture_demo.infra.persistence.PersonRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class IntegrationTestBase {

    protected static final String PERSON_API = "/api/persons";

    @LocalServerPort
    protected int port;

    @Autowired
    protected PersonRepository personRepository;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();

        personRepository.deleteAll();
    }

    protected int countAddresses() {
        Integer total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM addresses", Integer.class);
        return total == null ? 0 : total;
    }
}
