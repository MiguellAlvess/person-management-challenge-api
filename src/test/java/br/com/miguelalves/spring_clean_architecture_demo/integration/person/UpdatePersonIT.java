package br.com.miguelalves.spring_clean_architecture_demo.integration.person;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.Test;

import br.com.miguelalves.spring_clean_architecture_demo.integration.config.IntegrationTestBase;
import br.com.miguelalves.spring_clean_architecture_demo.utils.TestPersonFactory;
import static io.restassured.RestAssured.given;

class UpdatePersonIT extends IntegrationTestBase {

        @Test
        void shouldUpdatePersonSuccessfully() {
                Map<String, Object> requestBody = TestPersonFactory.createPersonWithoutAddressesRequest(
                                "Juliana Alves",
                                LocalDate.now().minusYears(32),
                                "33344455566");
                Long id = given()
                                .body(requestBody)
                                .when()
                                .post(PERSON_API)
                                .then().statusCode(201)
                                .extract().jsonPath().getLong("id");
                Map<String, Object> updateBody = TestPersonFactory.updatePersonRequest(
                                "Juliana Almeida",
                                LocalDate.now().minusYears(32),
                                "33344455566",
                                List.of(TestPersonFactory.address("Rua Nova", "10", "Vila Nova", "Rio de Janeiro", "RJ",
                                                "22040-020")));
                given()
                                .pathParam("id", id)
                                .body(updateBody)
                                .when()
                                .put(PERSON_API + "/{id}")
                                .then().log().ifValidationFails()
                                .statusCode(200)
                                .body("id", equalTo(id.intValue()))
                                .body("name", equalTo("Juliana Almeida"))
                                .body("addresses", hasSize(1));

                assertThat(personRepository.findById(id)).isPresent();
                assertThat(personRepository.findById(id).get().getName()).isEqualTo("Juliana Almeida");
        }

        @Test
        void shouldReturnNotFoundWhenUpdatingNonExistentPerson() {
                Map<String, Object> updateBody = TestPersonFactory.updatePersonRequest(
                                "Caio Lima",
                                LocalDate.now().minusYears(31),
                                "66677788899",
                                List.of(TestPersonFactory.address("Rua Fantasia", "99", "Centro", "Manaus", "AM",
                                                "69000-000")));
                given()
                                .pathParam("id", 9999)
                                .body(updateBody)
                                .when()
                                .put(PERSON_API + "/{id}")
                                .then().log().ifValidationFails()
                                .statusCode(404)
                                .body("error", equalTo("Person not found"));
        }

        @Test
        void shouldReturnBadRequestWhenUpdateRequestHasMissingRequiredFields() {
                Map<String, Object> requestBody = TestPersonFactory.updatePersonRequest(
                                "",
                                LocalDate.now().minusYears(29),
                                "",
                                List.of(TestPersonFactory.address("Rua Teste", "12", "Centro", "Belo Horizonte", "MG",
                                                "30110-000")));
                given()
                                .pathParam("id", 1)
                                .body(requestBody)
                                .when()
                                .put(PERSON_API + "/{id}")
                                .then().log().ifValidationFails()
                                .statusCode(400)
                                .body("error", equalTo("Validation failed"))
                                .body("details", hasItem(containsString("name: must not be blank")))
                                .body("details", hasItem(containsString("cpf: must not be blank")));
        }
}
