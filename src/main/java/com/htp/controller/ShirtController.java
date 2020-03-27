package com.htp.controller;

import com.htp.domain.BlankShirt;
import com.htp.repository.BlankShirtRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/admin/tee_shirt")
@RequiredArgsConstructor
public class ShirtController {

  private final BlankShirtRepository shirtRepo;

  //    @PostMapping("/")
  //    @Transactional
  //    public ResponseEntity<BlankShirt> createNewShirt() {
  //        return new ResponseEntity<>(new BlankShirt(), HttpStatus.CREATED);
  //    }

  @ApiOperation(value = "Find blank tee-shirt by id")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful getting tee-shirt"),
    @ApiResponse(code = 404, message = "Tee-shirt not found"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @GetMapping("/{id}")
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
  @GetMapping("/size")
  public ResponseEntity<List<BlankShirt>> blankShirtBySize(
      @ApiParam(value = "Size by which need to filter blank tee-shirts")
          @RequestParam(name = "size", defaultValue = "M")
          BlankShirt.Size size) {
    List<BlankShirt> shirts = shirtRepo.findBySize(size);
    if (!shirts.isEmpty()) {
      return new ResponseEntity<>(shirts, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  @ApiOperation(value = "Blank tee-shirts with quantity between max and min")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful getting tee-shirts with selected quantity"),
    @ApiResponse(code = 204, message = "Tee-shirts between max and min quantity not found"),
    @ApiResponse(code = 400, message = "Invalid parameter value"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @GetMapping("/quantity")
  public ResponseEntity<List<BlankShirt>> blankShirtQuantityFilter(
      @ApiParam(value = "min quantity") @RequestParam(name = "min") String min,
      @ApiParam(value = "max quantity") @RequestParam(name = "max") String max) {
    try {
      List<BlankShirt> shirts =
          shirtRepo.findBetweenQuantity(Integer.valueOf(min), Integer.valueOf(max));
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
  @GetMapping("/color")
  public ResponseEntity<List<BlankShirt>> blankShirtByColor(
      @ApiParam(value = "Colors by which need to filter blank tee-shirts")
          @RequestParam(name = "colors")
          List<String> colorList) {
    List<BlankShirt> shirts = shirtRepo.findByColor(colorList);
    if (!shirts.isEmpty()) {
      return new ResponseEntity<>(shirts, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }
}
