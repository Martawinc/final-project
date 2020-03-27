package com.htp.controller;

import com.htp.domain.Color;
import com.htp.repository.ColorRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/admin/color")
@RequiredArgsConstructor
public class ColorController {

  private final ColorRepository colorRepo;

  @ApiOperation(value = "Find color by id")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful getting color"),
    @ApiResponse(code = 400, message = "Invalid color id"),
    @ApiResponse(code = 404, message = "Color not found"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @GetMapping("/{id}")
  public ResponseEntity<Color> colorById(
      @ApiParam(value = "Id of color that need to be found") @PathVariable("id") String id) {
    try {
      Optional<Color> color = colorRepo.findById(Long.valueOf(id));
      return color
          .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
          .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    } catch (NumberFormatException ex) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  // no transaction
  @ApiOperation(value = "Create new tee-shirt color", notes = "This can only be done by admin")
  @PostMapping()
  @Transactional
  public ResponseEntity<Color> createNewColor(
      @ApiParam(value = "New color name", required = true) @RequestBody String colorName) {
    Color color = new Color();
    color.setColorName(colorName);
    return new ResponseEntity<>(colorRepo.saveAndFlush(color), HttpStatus.CREATED);
  }
}
