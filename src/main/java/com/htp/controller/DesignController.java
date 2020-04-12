package com.htp.controller;

import com.htp.config.PriceListConfig;
import com.htp.controller.requests.DesignCreateRequest;
import com.htp.controller.requests.DesignUpdateRequest;
import com.htp.domain.Color;
import com.htp.domain.DesignShirt;
import com.htp.repository.BlankShirtRepository;
import com.htp.repository.DesignShirtRepository;
import com.htp.service.AmazonUploadFileService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
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
  private final PriceListConfig priceList;
  private final BlankShirtRepository blankShirtRepo;

  @PostMapping()
  @Transactional(rollbackOn = Exception.class)
  @ApiOperation(
      value = "Create new design shirt",
      notes = "Choose color and size for tee-shirt and add your text print")
  @ApiResponses({
    @ApiResponse(code = 201, message = "New designed tee-shirt is created"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  public ResponseEntity<DesignShirt> createDesignShirt(
      @RequestBody @Valid DesignCreateRequest request) {
    DesignShirt convertedDesignShirts = conversionService.convert(request, DesignShirt.class);
    return new ResponseEntity<>(
        designShirtRepo.saveAndFlush(convertedDesignShirts), HttpStatus.CREATED);
  }

  @PutMapping()
  @Transactional(rollbackOn = Exception.class)
  @ApiOperation(
      value = "Update selected design shirt",
      notes = "Can change color, size or text print")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Designed tee-shirt is updated"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  public ResponseEntity<DesignShirt> updateDesignShirt(
      @RequestBody @Valid DesignUpdateRequest request) {
    DesignShirt convertedDesignShirts = conversionService.convert(request, DesignShirt.class);
    return new ResponseEntity<>(
        designShirtRepo.saveAndFlush(convertedDesignShirts), HttpStatus.OK);
  }

  @PutMapping("/image/{id}")
  @Transactional(rollbackOn = Exception.class)
  @ApiOperation(value = "Attach image for your design tee-shirt")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Image saved for selected designed tee-shirt"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  public ResponseEntity<DesignShirt> saveUpdateImage(
      @ApiParam(value = "Id of tee-shirt you add image print") @PathVariable("id") String id,
      @RequestBody MultipartFile image) throws IOException {

    DesignShirt designShirt =
        designShirtRepo.findById(Long.valueOf(id)).orElseThrow(() -> new EntityNotFoundException());

    if (designShirt.getImageLink() == null) {
      float price = designShirt.getTotalPrice() + priceList.getImagePrice();
      designShirt.setTotalPrice(price);
    }
    designShirt.setImageLink(
        amazonUploadFileService.uploadFile(image.getBytes(), Long.valueOf(id)));

    return new ResponseEntity<>(designShirtRepo.saveAndFlush(designShirt), HttpStatus.OK);
  }

  @ApiOperation(
      value = "Filter design tee-shirts by color list",
      notes = "Can be provided with multiple colors")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful getting tee-shirts with selected colors"),
    @ApiResponse(code = 204, message = "Tee-shirts with selected colors not found"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
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

  @ApiOperation(
      value = "Select page number and quantity of entities per page",
      notes = "descending sort type by date of creation")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful getting selected page of design tee-shirts"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "page", dataType = "integer", paramType = "query",
        value = "Page number you want navigate to (begins with 0)"),
    @ApiImplicitParam(
        name = "size", dataType = "integer", paramType = "query",
        value = "Quantity of records per page")
  })
  @GetMapping("/all")
  public ResponseEntity<Page<DesignShirt>> getDesignShirtPage(@ApiIgnore Pageable pageable) {
    Pageable descPage =
        PageRequest.of(
            pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "creationDate");
    return new ResponseEntity<>(designShirtRepo.findByDeletedFalse(descPage), HttpStatus.OK);
  }

  @ApiOperation(value = "Delete designed tee-shirt by id")
  @ApiResponses({
    @ApiResponse(code = 204, message = "Successful deleting tee-shirt"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @Transactional(rollbackOn = Exception.class)
  @DeleteMapping("/{id}")
  public ResponseEntity<Color> deleteDesignShirt(
      @ApiParam(value = "Id of tee-shirt that need to be deleted") @PathVariable("id") String id) {

    DesignShirt designShirt =
        designShirtRepo.findById(Long.valueOf(id)).orElseThrow(() -> new EntityNotFoundException());

    designShirt.setDeleted(true);
    blankShirtRepo.updateQuantity(
        designShirt.getShirt().getQuantity() + 1, designShirt.getShirt().getId());
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }
}
