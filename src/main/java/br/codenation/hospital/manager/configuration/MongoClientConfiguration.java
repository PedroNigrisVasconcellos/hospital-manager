package br.codenation.hospital.manager.configuration;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
@Profile(value = "prod")
public class MongoClientConfiguration extends AbstractMongoConfiguration {

  @Value("${spring.data.mongodb.host}")
  private String host;

  @Value("${spring.data.mongodb.port")
  private int port;

  @Value("${spring.data.mongodb.database}")
  private String database;

  @Override
  public MongoClient mongoClient() {
    return new MongoClient(host, port);
  }

  @Override
  protected String getDatabaseName() {
    return database;
  }
}
