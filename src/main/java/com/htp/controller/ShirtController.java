package com.htp.controller;

import com.htp.controller.requests.ShirtCreateRequest;
import com.htp.domain.BlankShirt;
import com.htp.domain.Color;
import com.htp.repository.BlankShirtRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping()
@RequiredArgsConstructor
public class ShirtController {

  private final BlankShirtRepository shirtRepo;

  private final ConversionService conversionService;

  @PostMapping("/admin/tee-shirt")
  @Transactional(rollbackOn = Exception.class)
  @ApiOperation(
      value = "Create new tee-shirt article from supplier with initial price and quantity",
      notes = "This can only be done by admin")
  @ApiResponses({
    @ApiResponse(code = 201, message = "New tee-shirt is created"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  public ResponseEntity<BlankShirt> createBlankShirt(
      @RequestBody @Valid ShirtCreateRequest request) {
    BlankShirt convertedShirt = conversionService.convert(request, BlankShirt.class);
    return new ResponseEntity<>(shirtRepo.saveAndFlush(convertedShirt), HttpStatus.CREATED);
  }

  @ApiOperation(value = "Find blank tee-shirt by id")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful getting tee-shirt"),
    @ApiResponse(code = 404, message = "Tee-shirt not found"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @GetMapping("/tee-shirt/{id}")
  public ResponseEntity<BlankShirt> blankShirtById(
      @ApiParam(value = "Id of tee-shirt that need to be found") @PathVariable("id") String id) {
    Optional<BlankShirt> shirt = shirtRepo.findById(id);
    return shirt
        .map(blankShirt -> new ResponseEntity<>(blankShirt, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
  }

  @ApiOperation(value = "Filter blank tee-shirts by size")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful getting tee-shirts with selected size"),
    @ApiResponse(code = 204, message = "Tee-shirts with selected size not found"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @GetMapping("/tee-shirt/size")
  public ResponseEntity<List<BlankShirt>> blankShirtBySize(
      @ApiParam(value = "Size by which need to filter blank tee-shirts")
          @RequestParam(name = "size")
          BlankShirt.Size size) {
    List<BlankShirt> shirts = shirtRepo.findBySize(size);
    if (!shirts.isEmpty()) {
      return new ResponseEntity<>(shirts, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  @ApiOperation(
      value = "Filter blank tee-shirts with quantity between max and min",
      notes = "This can only be done by admin")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful getting tee-shirts with selected quantity"),
    @ApiResponse(code = 204, message = "Tee-shirts between max and min quantity not found"),
    @ApiResponse(code = 400, message = "Invalid parameter value"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @GetMapping("admin/tee-shirt/quantity")
  public ResponseEntity<List<BlankShirt>> blankShirtQuantityFilter(
      @ApiParam(value = "min quantity") @RequestParam(name = "min") String min,
      @ApiParam(value = "max quantity") @RequestParam(name = "max") String max) {
    try {
      List<BlankShirt> shirts =
          shirtRepo.findByQuantityBetween(Integer.parseInt(min), Integer.valueOf(max));
      if (!shirts.isEmpty()) {
        return new ResponseEntity<>(shirts, HttpStatus.OK);
      }
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    } catch (NumberFormatException ex) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  @ApiOperation(
      value = "Filter blank tee-shirts by color list",
      notes = "Can be provided with multiple colors")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful getting tee-shirts with selected colors"),
    @ApiResponse(code = 204, message = "Tee-shirts with selected colors not found"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @GetMapping("/tee-shirt/color")
  public ResponseEntity<List<BlankShirt>> blankShirtByColor(
      @ApiParam(value = "Colors by which need to filter blank tee-shirts")
          @RequestParam(name = "colors") List<String> colorList) {
    List<BlankShirt> shirts = shirtRepo.findByColor(colorList);
    if (!shirts.isEmpty()) {
      return new ResponseEntity<>(shirts, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  @ApiOperation(value = "Filter blank tee-shirts with price between max and min")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful getting tee-shirts with selected price"),
    @ApiResponse(code = 204, message = "Tee-shirts between max and min price not found"),
    @ApiResponse(code = 400, message = "Invalid parameter value"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @GetMapping("/tee-shirt/price")
  public ResponseEntity<List<BlankShirt>> blankShirtPriceFilter(
      @ApiParam(value = "min price") @RequestParam(name = "min") String min,
      @ApiParam(value = "max price") @RequestParam(name = "max") String max) {
    try {
      List<BlankShirt> shirts =
          shirtRepo.findByPriceBetween(Float.valueOf(min), Float.valueOf(max));
      if (!shirts.isEmpty()) {
        return new ResponseEntity<>(shirts, HttpStatus.OK);
      }
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    } catch (NumberFormatException ex) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  @ApiOperation(value = "Update tee-shirt quantity by id", notes = "This can only be done by admin")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful updating quantity"),
    @ApiResponse(code = 400, message = "Invalid parameter value"),
    @ApiResponse(code = 404, message = "Tee-shirt not found"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @Transactional(rollbackOn = Exception.class)
  @PutMapping("admin/tee-shirt/quantity/{id}")
  public ResponseEntity<BlankShirt> updateQuantity(
      @ApiParam(value = "Id of tee-shirt that need to be updated") @PathVariable("id") String id,
      @ApiParam(value = "New quantity") @RequestParam String quantity) {
    try {
      Optional<BlankShirt> shirtOptional = shirtRepo.findById(id);
      if (shirtOptional.isPresent()) {
        shirtRepo.updateQuantity(Integer.valueOf(quantity), id);
        shirtRepo.flush();
        return new ResponseEntity<>(shirtRepo.findById(id).get(), HttpStatus.OK);
      }
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } catch (NumberFormatException ex) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  @ApiOperation(
      value = "Batch update tee-shirt price by ids",
      notes = "This can only be done by admin")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful updating price"),
    @ApiResponse(code = 400, message = "Invalid parameter value"),
    @ApiResponse(code = 404, message = "Tee-shirt not found"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @Transactional(rollbackOn = Exception.class)
  @PutMapping("admin/tee-shirt/price")
  public ResponseEntity<List<BlankShirt>> updatePriceByIdList(
      @ApiParam(value = "List of tee-shirt ids that need to be updated") @RequestParam
          List<String> ids,
      @ApiParam(value = "New price") @RequestParam String price) {
    try {
      List<BlankShirt> shirts = shirtRepo.findByIdIn(ids);
      if (!shirts.isEmpty()) {
        for (BlankShirt shirtForUpdate : shirts) {
          shirtRepo.updatePrice(Float.valueOf(price), shirtForUpdate.getId());
        }
        shirtRepo.flush();
        return new ResponseEntity<>(shirtRepo.findByIdIn(ids), HttpStatus.OK);
      }
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    } catch (NumberFormatException ex) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  @ApiOperation(value = "Delete tee-shirt by id", notes = "no rollback deleting")
  @ApiResponses({
    @ApiResponse(code = 204, message = "Successful deleting tee-shirt"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @Transactional(rollbackOn = Exception.class)
  @DeleteMapping("admin/tee-shirt/{id}")
  public ResponseEntity<Color> deleteBlankShirt(
      @ApiParam(value = "Id of tee-shirt that need to be deleted") @PathVariable("id") String id) {

    Optional<BlankShirt> shirt = shirtRepo.findById(id);
    if (shirt.isPresent()) {
      shirtRepo.delete(shirt.get());
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }
}
