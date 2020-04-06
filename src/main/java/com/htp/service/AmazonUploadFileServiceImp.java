package com.htp.service;

import com.htp.config.AmazonS3Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class AmazonUploadFileServiceImp implements AmazonUploadFileService {

  private final S3AsyncClient s3AsyncClient;
  private final AmazonS3Config amazonS3Config;

  @Override
  public String uploadFile(byte[] image) throws InterruptedException, ExecutionException {
    String imageUUID = UUID.randomUUID().toString();
    CompletableFuture<PutObjectResponse> futureGet =
        s3AsyncClient.putObject(
            PutObjectRequest.builder()
                .bucket(amazonS3Config.getBucket())
                .contentType("image.jpg")
                .contentLength((long) image.length)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .key(String.format("%s/%s/%s.jpg", amazonS3Config.getFolder(), "new folder", "cats"/*imageUUID*/))
                .build(),
            AsyncRequestBody.fromBytes(image));
    futureGet.get();
    return null;
  }
}
