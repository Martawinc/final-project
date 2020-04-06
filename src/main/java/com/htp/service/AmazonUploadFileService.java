package com.htp.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface AmazonUploadFileService {
    String uploadFile (byte [] image) throws InterruptedException, ExecutionException, IOException; // not only io
}
