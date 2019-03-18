package helper;

import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.model.Product;

import java.util.Random;
import java.util.UUID;

public final class TestHelper {

  private static final Random RANDOM = new Random();

  private TestHelper() {}

  public static Hospital newHospital() {
    return new Hospital(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        (long) RANDOM.nextInt(100),
        (long) RANDOM.nextInt(100));
  }

  public static Product newProduct() {
    return new Product(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 10L);
  }
}
