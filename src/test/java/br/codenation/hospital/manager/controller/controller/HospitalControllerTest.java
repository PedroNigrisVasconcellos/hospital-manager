package br.codenation.hospital.manager.controller.controller;

import br.codenation.hospital.manager.controller.HospitalController;
import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.repository.HospitalRepository;
import helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebFluxTest(HospitalController.class)
public class HospitalControllerTest {

  @Autowired private WebTestClient webTestClient;

  @MockBean private HospitalRepository hospitalRepository;

  @Test
  public void createHospital() {

    final Hospital hospital = TestHelper.newHospital();

    when(hospitalRepository.save(hospital)).thenReturn(Mono.just(hospital));

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
        .jsonPath("$.name")
        .isEqualTo(hospital.getName())
        .jsonPath("$.address")
        .isEqualTo(hospital.getAddress())
        .jsonPath("$.beds")
        .isEqualTo(hospital.getBeds())
        .jsonPath("$.availableBeds")
        .isEqualTo(hospital.getAvailableBeds());
  }

  //  @Test
  //  public void getSingleHospital() {
  //
  //    webTestClient
  //        .get()
  //        .uri("/v1/hospitais/{id}", Collections.singletonMap("id", hospital.getId()))
  //        .exchange()
  //        .expectStatus()
  //        .isOk()
  //        .expectBody()
  //        .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNotNull())
  //        .jsonPath("$.id")
  //        .isNotEmpty()
  //        .jsonPath("$.name")
  //        .isEqualTo(hospital.getName())
  //        .jsonPath("$.address")
  //        .isEqualTo(hospital.getAddress())
  //        .jsonPath("$.beds")
  //        .isEqualTo(hospital.getBeds())
  //        .jsonPath("$.availableBeds")
  //        .isEqualTo(hospital.getAvailableBeds());
  //  }
}
