package br.codenation.hospital.manager.controller;

import br.codenation.hospital.manager.exception.ResourceNotFoundException;
import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.service.HospitalService;
import helper.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class HospitalControllerTest {

  private static final String BASE_PATH = "/v1/hospitais";

  @Mock private HospitalService hospitalService;

  private MockMvc mockMvc;
  private String hospitalId;

  @BeforeEach
  public void setUp() {

    final HospitalController controller = new HospitalController(hospitalService);

    hospitalId = UUID.randomUUID().toString();

    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
  }

  @Test
  public void createHospital() throws Exception {

    final Hospital hospital = TestHelper.newHospital();

    final MvcResult mvcResult =
        mockMvc
            .perform(
                post(BASE_PATH)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(TestHelper.getMapper().writeValueAsString(hospital)))
            .andExpect(request().asyncStarted())
            .andReturn();

    mockMvc.perform(asyncDispatch(mvcResult)).andExpect(status().isCreated());
  }

  @Test
  public void shouldBadRequestCreateHospital() throws Exception {
    mockMvc
        .perform(
            post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andReturn();
  }

  @Test
  public void shouldUnprocessableEntityCreateHospital() throws Exception {

    Hospital hospital = TestHelper.newHospital();
    hospital.setName(null);

    mockMvc
        .perform(
            post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestHelper.getMapper().writeValueAsString(hospital))
                .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$.message", is("validation error")))
        .andExpect(jsonPath("$.errors", hasSize(2)))
        .andExpect(jsonPath("$.errors[0].fieldName", is("name")))
        .andExpect(jsonPath("$.errors[1].fieldName", is("name")))
        .andReturn();
  }

  @Test
  public void hospitalNotFound() throws Exception {

    when(hospitalService.loadHospital(eq(hospitalId)))
        .thenThrow(new ResourceNotFoundException("mock-exception"));

    final MvcResult mvcResult =
        mockMvc
            .perform(get(BASE_PATH + "/" + hospitalId).contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(request().asyncStarted())
            .andReturn();

    mockMvc.perform(asyncDispatch(mvcResult)).andExpect(status().isNotFound());
  }

  @Test
  public void hospitalFound() throws Exception {

    final Hospital hospital = TestHelper.newHospital();

    when(hospitalService.loadHospital(eq(hospitalId))).thenReturn(hospital);

    final MvcResult mvcResult =
        mockMvc
            .perform(get(BASE_PATH + "/" + hospitalId).contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(request().asyncStarted())
            .andReturn();

    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(hospital.getName())))
        .andExpect(jsonPath("$.address", is(hospital.getAddress())))
        .andExpect(jsonPath("$.beds", is(hospital.getBeds().intValue())))
        .andExpect(jsonPath("$.availableBeds", is(hospital.getAvailableBeds().intValue())))
        .andExpect(jsonPath("$.stock", is(hospital.getStock())))
        .andExpect(jsonPath("$.patients", is(hospital.getPatients())));
  }

  @Test
  public void shouldAvailableBedsCount() throws Exception {

    final Hospital hospital = TestHelper.newHospital();

    when(hospitalService.loadHospital(eq(hospitalId))).thenReturn(hospital);

    final MvcResult mvcResult =
        mockMvc
            .perform(
                get(BASE_PATH + "/" + hospitalId + "/leitos")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(request().asyncStarted())
            .andReturn();

    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is(hospital.getAvailableBeds().intValue())));
  }
}
