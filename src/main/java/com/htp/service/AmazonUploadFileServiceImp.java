package com.htp.service;

import com.htp.config.AmazonS3Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class AmazonUploadFileServiceImp implements AmazonUploadFileService {

	private final S3AsyncClient s3AsyncClient;
	private final AmazonS3Config amazonS3Config;

	@Override
	public String uploadFile(BiConsumer<? super PutObjectResponse, ? super Throwable> action, byte[] image, Long id) {

		CompletableFuture<PutObjectResponse> responseFuture =
				s3AsyncClient.putObject(
						PutObjectRequest.builder()
								.bucket(amazonS3Config.getBucket())
								.contentType("image.jpg")
								.contentLength((long) image.length)
								.acl(ObjectCannedACL.PUBLIC_READ)
								.key(String.format("%s/%s.jpg", amazonS3Config.getFolder(), id))
								.build(),
						AsyncRequestBody.fromBytes(image));

		responseFuture.whenComplete(action);

		return String.format(
				"%s/%s/%s/%s.jpg",
				amazonS3Config.getServerURL(), amazonS3Config.getBucket(), amazonS3Config.getFolder(), id);
	}
}
