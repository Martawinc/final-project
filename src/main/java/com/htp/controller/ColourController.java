package com.htp.controller;

import com.htp.domain.Colour;
import com.htp.repository.ColourRepository;
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

    @PostMapping("/{colourName}")
    @Transactional
    public ResponseEntity<Colour> createNewColour(@PathVariable("colourName") String colourName) {
        Colour colour = new Colour();
        colour.setColorName(colourName);
        return new ResponseEntity<>(colourRepo.saveAndFlush(colour), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colour> colourById(@PathVariable Long id) {
        Optional<Colour> colour = colourRepo.findById(id);
        if (colour.isPresent()) {
            return new ResponseEntity<>(colour.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
