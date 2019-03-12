package br.codenation.hospital.manager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HospitalControllerTest {

  private HospitalController controller;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {

    controller = new HospitalController();

    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
  }

  @Test
  public void getHospitalInformation() throws Exception {

    final MvcResult mvcResult =
        mockMvc
            .perform(get("/v1/hospitais/123").contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(request().asyncStarted())
            .andReturn();

    mockMvc.perform(asyncDispatch(mvcResult)).andExpect(status().isOk());
  }
}
