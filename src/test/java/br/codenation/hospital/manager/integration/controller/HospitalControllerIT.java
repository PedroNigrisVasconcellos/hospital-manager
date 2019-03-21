package br.codenation.hospital.manager.integration.controller;

import br.codenation.hospital.manager.model.Hospital;
import helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HospitalControllerIT {

  private static final String BASE_PATH = "/v1/hospitais";

  @Autowired private MockMvc mockMvc;

  @Test
  public void createAndRetrieveHospital() throws Exception {

    final Hospital hospital = TestHelper.newHospital();

    MvcResult mvcResult =
        mockMvc
            .perform(
                post(BASE_PATH)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(TestHelper.getMapper().writeValueAsString(hospital)))
            .andExpect(request().asyncStarted())
            .andReturn();

    mvcResult =
        mockMvc.perform(asyncDispatch(mvcResult)).andExpect(status().isCreated()).andReturn();

    final Hospital savedHospital =
        TestHelper.getMapper()
            .readValue(mvcResult.getResponse().getContentAsByteArray(), Hospital.class);

    mvcResult =
        mockMvc
            .perform(
                get(BASE_PATH + "/" + savedHospital.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(request().asyncStarted())
            .andReturn();

    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(savedHospital.getId())))
        .andExpect(jsonPath("$.name", is(savedHospital.getName())))
        .andExpect(jsonPath("$.address", is(savedHospital.getAddress())))
        .andExpect(jsonPath("$.beds", is(savedHospital.getBeds().intValue())))
        .andExpect(jsonPath("$.availableBeds", is(savedHospital.getAvailableBeds().intValue())))
        .andExpect(jsonPath("$.stock", is(savedHospital.getStock())))
        .andExpect(jsonPath("$.patients", is(savedHospital.getPatients())));
  }

  @Test
  public void hospitalNotFound() throws Exception {

    final MvcResult mvcResult =
        mockMvc
            .perform(get(BASE_PATH + "/" + "123").contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(request().asyncStarted())
            .andReturn();

    mockMvc.perform(asyncDispatch(mvcResult)).andExpect(status().isNotFound());
  }
}
