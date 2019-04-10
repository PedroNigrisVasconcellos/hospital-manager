package helper;

import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.model.Patient;
import br.codenation.hospital.manager.model.product.SupplyItem;
import br.codenation.hospital.manager.model.product.SupplyType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public final class TestHelper {

  private static final Random RANDOM = new Random();
  private static final ObjectMapper MAPPER = new ObjectMapper();

  private TestHelper() {}

  public static Hospital newHospital() {
    return new Hospital(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        (long) RANDOM.nextInt(100),
        (long) RANDOM.nextInt(100),
        RANDOM.nextDouble(),
        RANDOM.nextDouble());
  }

  public static Patient newPatient() {
    return new Patient(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        LocalDate.now(),
        "female",
        LocalDate.now(),
        RANDOM.nextDouble(),
        RANDOM.nextDouble());
  }

  public static SupplyItem newProduct() {
    final List<SupplyType> supplyTypes = Arrays.asList(SupplyType.values());

    Collections.shuffle(supplyTypes);

    return new SupplyItem(
        UUID.randomUUID().toString(), UUID.randomUUID().toString(), 10L, supplyTypes.get(0));
  }

  public static ObjectMapper getMapper() {
    MAPPER.registerModule(new JavaTimeModule());
    MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return MAPPER;
  }
}
