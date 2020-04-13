package com.htp.service;

import java.io.IOException;

public interface AmazonUploadFileService {
  String uploadFile(byte[] image, Long id) throws IOException;
}
