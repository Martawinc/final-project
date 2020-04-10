package com.htp.controller;

import com.htp.controller.requests.DesignCreateRequest;
import com.htp.domain.DesignShirt;
import com.htp.repository.DesignShirtRepository;
import com.htp.service.AmazonUploadFileService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/design")
@RequiredArgsConstructor
public class DesignController {

  private final DesignShirtRepository designShirtRepo;
  private final ConversionService conversionService;
  private final AmazonUploadFileService amazonUploadFileService;

  @PostMapping("/create")
  @Transactional
  public ResponseEntity<DesignShirt> createDesignShirt(
      @RequestBody @Valid DesignCreateRequest request) {
    DesignShirt convertedDesignShirts = conversionService.convert(request, DesignShirt.class);
    return new ResponseEntity<>(
        designShirtRepo.saveAndFlush(convertedDesignShirts), HttpStatus.CREATED);
  }

  @PostMapping("/image/{id}")
  public ResponseEntity<DesignShirt> saveUpdateImage(
      @PathVariable String id, @RequestBody MultipartFile image) throws IOException {

    DesignShirt designShirt =
        designShirtRepo.findById(Long.valueOf(id)).orElseThrow(() -> new EntityNotFoundException());

    designShirt.setImageLink(
        amazonUploadFileService.uploadFile(image.getBytes(), Long.valueOf(id)));

    return new ResponseEntity<>(designShirtRepo.saveAndFlush(designShirt), HttpStatus.OK);
  }

  @GetMapping("/color")
  public ResponseEntity<List<DesignShirt>> designShirtByColor(
      @ApiParam(value = "Color by which need to filter design tee-shirts")
          @RequestParam(name = "colorList") List<String> colorList) {

    List<DesignShirt> designShirts = designShirtRepo.findByColor(colorList);
    if (!designShirts.isEmpty()) {
      return new ResponseEntity<>(designShirts, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }
}
