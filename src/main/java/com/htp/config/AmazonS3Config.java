package com.htp.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties("amazon")
public class AmazonS3Config {
  private String serverURL;
  private String awsAccessKeyId;
  private String awsSecretKey;
  private String region;
  private String folder;
  private String bucket;

  @Bean
  public S3AsyncClient s3Client() {
    return S3AsyncClient.builder()
        .region(Region.of(region))
        .credentialsProvider(() -> AwsBasicCredentials.create(awsAccessKeyId, awsSecretKey))
        .build();
  }
}
