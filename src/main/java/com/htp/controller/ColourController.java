package com.htp.controller;

import com.htp.domain.Colour;
import com.htp.repository.ColourRepository;
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
@RequestMapping(value = "/admin/colour")
@RequiredArgsConstructor
public class ColourController {

    private final ColourRepository colourRepo;

    // no transaction
    @ApiOperation(value = "Create new tee-shirt colour",
            notes = "This can only be done by admin")
    @PostMapping()
    @Transactional
    public ResponseEntity<Colour> createNewColour(
            @ApiParam(value = "New color name", required = true)
            @RequestBody String colourName) {
        Colour colour = new Colour();
        colour.setColorName(colourName);
        return new ResponseEntity<>(colourRepo.saveAndFlush(colour), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Find Colour by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful getting colour"),
            @ApiResponse(code = 400, message = "Invalid Colour id"),
            @ApiResponse(code = 404, message = "Colour not found"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Colour> colourById(
            @ApiParam(value = "Id of colour that need to be found")
            @PathVariable("id") String id) {
        try {
            Optional<Colour> colour = colourRepo.findById(Long.valueOf(id));
            if (colour.isPresent()) {
                return new ResponseEntity<>(colour.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (NumberFormatException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
