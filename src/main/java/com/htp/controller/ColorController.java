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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

  @ApiOperation(value = "Create new tee-shirt color", notes = "This can only be done by admin")
  @ApiResponses({
    @ApiResponse(code = 201, message = "New color is created"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @Transactional(rollbackOn = Exception.class)
  @PostMapping()
  public ResponseEntity<Color> createNewColor(
      @ApiParam(value = "New color name", required = true) @RequestParam String colorName) {
    Color color = new Color();
    color.setColorName(colorName);
    return new ResponseEntity<>(colorRepo.saveAndFlush(color), HttpStatus.CREATED);
  }

  @ApiOperation(value = "Update tee-shirt color name", notes = "This can only be done by admin")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful updating color name"),
    @ApiResponse(code = 400, message = "Invalid color id"),
    @ApiResponse(code = 404, message = "Color not found"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @Transactional(rollbackOn = Exception.class)
  @PutMapping("/{id}")
  public ResponseEntity<Color> updateColorName(
      @ApiParam(value = "Id of color that need to be updated") @PathVariable("id") String id,
      @ApiParam(value = "New color name", required = true) @RequestParam String colorName) {
    try {
      Optional<Color> colorOptional = colorRepo.findById(Long.valueOf(id));
      if (colorOptional.isPresent()) {
        Color color = colorOptional.get();
        color.setColorName(colorName);
        return new ResponseEntity<>(colorRepo.saveAndFlush(color), HttpStatus.OK);
      }
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } catch (NumberFormatException ex) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  @ApiOperation(value = "Delete color by id", notes = "no rollback deleting")
  @ApiResponses({
    @ApiResponse(code = 204, message = "Successful deleting color"),
    @ApiResponse(code = 400, message = "Invalid color id"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @Transactional(rollbackOn = Exception.class)
  @DeleteMapping("/{id}")
  public ResponseEntity<Color> deleteColor(
      @ApiParam(value = "Id of color that need to be deleted") @PathVariable("id") String id) {
    try {
      Optional<Color> color = colorRepo.findById(Long.valueOf(id));
      if (color.isPresent()) {
        colorRepo.delete(color.get());
      }
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    } catch (NumberFormatException ex) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
