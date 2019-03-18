package br.codenation.hospital.manager.controller.integration;

import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.model.Product;
import br.codenation.hospital.manager.repository.HospitalRepository;
import br.codenation.hospital.manager.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import helper.TestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HospitalControllerIT {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Autowired private WebTestClient webTestClient;

  @Autowired private HospitalRepository hospitalRepository;
  @Autowired private ProductRepository productRepository;

  @Test
  public void createHospital() throws Exception {
    final Hospital hospital = TestHelper.newHospital();

    final EntityExchangeResult<byte[]> result =
        webTestClient
            .post()
            .uri("/v1/hospitais")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just(hospital), Hospital.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBody()
            .jsonPath("$.id")
            .isNotEmpty()
            .jsonPath("$.name")
            .isEqualTo(hospital.getName())
            .jsonPath("$.address")
            .isEqualTo(hospital.getAddress())
            .jsonPath("$.beds")
            .isEqualTo(hospital.getBeds())
            .jsonPath("$.availableBeds")
            .isEqualTo(hospital.getAvailableBeds())
            .returnResult();

    final Hospital returnedHospital =
        OBJECT_MAPPER.readValue(result.getResponseBody(), Hospital.class);

    assertEquals(returnedHospital, hospitalRepository.findById(returnedHospital.getId()).block());
  }

  @Test
  public void getSingleHospital() {
    final Hospital hospital = hospitalRepository.save(TestHelper.newHospital()).block();

    webTestClient
        .get()
        .uri("/v1/hospitais/{id}", Collections.singletonMap("id", hospital.getId()))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNotNull())
        .jsonPath("$.id")
        .isNotEmpty()
        .jsonPath("$.name")
        .isEqualTo(hospital.getName())
        .jsonPath("$.address")
        .isEqualTo(hospital.getAddress())
        .jsonPath("$.beds")
        .isEqualTo(hospital.getBeds())
        .jsonPath("$.availableBeds")
        .isEqualTo(hospital.getAvailableBeds());
  }

  @Test
  public void createHospitalStock() throws Exception {

    final Hospital hospital = TestHelper.newHospital();

    final EntityExchangeResult<byte[]> hospitalResult =
        webTestClient
            .post()
            .uri("/v1/hospitais")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just(hospital), Hospital.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBody()
            .returnResult();

    final Hospital returnedHospital =
        OBJECT_MAPPER.readValue(hospitalResult.getResponseBody(), Hospital.class);

    webTestClient
        .get()
        .uri("/v1/hospitais/{id}/estoque", Collections.singletonMap("id", returnedHospital.getId()))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNullOrEmpty())
        .returnResult();

    final Product product = TestHelper.newProduct();

    final EntityExchangeResult<byte[]> productResult =
        webTestClient
            .post()
            .uri(
                "/v1/hospitais/{id}/estoque",
                Collections.singletonMap("id", returnedHospital.getId()))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just(product), Product.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBody()
            .returnResult();

    final Product returnedProduct =
        OBJECT_MAPPER.readValue(productResult.getResponseBody(), Product.class);

    System.out.println("HERE");
  }
}
