package br.codenation.hospital.manager.controller;

import br.codenation.hospital.manager.exception.ResourceNotFoundException;
import br.codenation.hospital.manager.model.Patient;
import br.codenation.hospital.manager.service.HospitalService;
import br.codenation.hospital.manager.service.PatientService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

  private static final String BASE_PATH = "/v1/hospitais";
  private static final String validCPF = "60602412048";

  @Mock private HospitalService hospitalService;
  @Mock private PatientService patientService;

  private MockMvc mockMvc;
  private String hospitalId;
  private String patientId;

  @BeforeEach
  public void setUp() {

    final PatientController controller = new PatientController(hospitalService, patientService);

    hospitalId = UUID.randomUUID().toString();
    patientId = UUID.randomUUID().toString();

    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
  }

  @Test
  public void shouldCreatePatient() throws Exception {

    final Patient patient = TestHelper.newPatient();
    patient.setHospitalCheckIn(null);
    patient.setCpf(validCPF);

    final MvcResult mvcResult =
        mockMvc
            .perform(
                post(BASE_PATH + "/pacientes")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(TestHelper.getMapper().writeValueAsString(patient))
                    .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(request().asyncStarted())
            .andReturn();

    mockMvc.perform(asyncDispatch(mvcResult)).andExpect(status().isCreated());
  }

  @Test
  public void shouldBadRequestCreatePatient() throws Exception {
    mockMvc
        .perform(
            post(BASE_PATH + "/pacientes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andReturn();
  }

  @Test
  public void shouldUnprocessableEntityCreatePatient() throws Exception {

    final Patient patient = TestHelper.newPatient();
    patient.setHospitalCheckIn(null);
    patient.setCpf(validCPF);
    patient.setFullName(null);

    mockMvc
        .perform(
            post(BASE_PATH + "/pacientes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestHelper.getMapper().writeValueAsString(patient))
                .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$.message", is("validation error")))
        .andExpect(jsonPath("$.errors", hasSize(2)))
        .andExpect(jsonPath("$.errors[0].fieldName", is("fullName")))
        .andExpect(jsonPath("$.errors[1].fieldName", is("fullName")))
        .andReturn();
  }

  @Test
  public void shouldPatientNotFound() throws Exception {

    when(hospitalService.findPatient(eq(patientId), eq(hospitalId)))
        .thenThrow(new ResourceNotFoundException("mock-exception"));

    final MvcResult mvcResult =
        mockMvc
            .perform(
                get(String.format("%s/%s/pacientes/%s", BASE_PATH, hospitalId, patientId))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(request().asyncStarted())
            .andReturn();

    mockMvc.perform(asyncDispatch(mvcResult)).andExpect(status().isNotFound());
  }

  @Test
  public void shouldPatientFound() throws Exception {

    Patient patient = TestHelper.newPatient();

    when(hospitalService.findPatient(eq(patientId), eq(hospitalId))).thenReturn(patient);

    final MvcResult mvcResult =
        mockMvc
            .perform(
                get(String.format("%s/%s/pacientes/%s", BASE_PATH, hospitalId, patientId))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(request().asyncStarted())
            .andReturn();

    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.fullName", is(patient.getFullName())));

    Patient patientFound =
        TestHelper.getMapper()
            .readValue(mvcResult.getResponse().getContentAsString(), Patient.class);

    assertEquals(patient.getFullName(), patientFound.getFullName());
  }
}
