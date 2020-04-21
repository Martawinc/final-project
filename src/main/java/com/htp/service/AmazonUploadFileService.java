package com.htp.service;

import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.function.BiConsumer;

public interface AmazonUploadFileService {
  String uploadFile(
      BiConsumer<? super PutObjectResponse, ? super Throwable> action, byte[] image, Long id)
      throws IOException;
}
