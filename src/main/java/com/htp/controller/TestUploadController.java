package com.htp.controller;

import com.htp.service.AmazonUploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestUploadController {

  private final AmazonUploadFileService amazonUploadFileService;

  @PostMapping
  public ResponseEntity<String> uploadImage(@RequestBody MultipartFile multipartFile)
      throws IOException, ExecutionException, InterruptedException {
    amazonUploadFileService.uploadFile(multipartFile.getBytes());
    return new ResponseEntity<>("imageLink", HttpStatus.OK);
  }
}
