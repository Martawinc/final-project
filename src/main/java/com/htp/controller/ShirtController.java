package com.htp.controller;

import com.htp.domain.BlankShirt;
import com.htp.repository.BlankShirtRepository;
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

    @GetMapping("/{id}")
    public ResponseEntity<BlankShirt> blankShirtById(@PathVariable String id) {
        Optional<BlankShirt> shirt = shirtRepo.findById(id);
        if (shirt.isPresent()) {
            return new ResponseEntity<>(shirt.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/size")
    public ResponseEntity<List<BlankShirt>> blankShirtBySize(@RequestParam(name = "size")
                                                                     BlankShirt.Size size) {
        List<BlankShirt> shirts = shirtRepo.findBySize(size);
        if (!shirts.isEmpty()) {
            return new ResponseEntity<>(shirts, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/quantity")
    public ResponseEntity<List<BlankShirt>> blankShirtQuantityFilter(@RequestParam(name = "quantity")
                                                                             int quantity) {
        List<BlankShirt> shirts = shirtRepo.findLowerQuantity(quantity);
        if (!shirts.isEmpty()) {
            return new ResponseEntity<>(shirts, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/colour")
    public ResponseEntity<List<BlankShirt>> blankShirtBySize(@RequestParam(name = "colour")
                                                                     String colour) {
        List<BlankShirt> shirts = shirtRepo.findByColour(colour);
        if (!shirts.isEmpty()) {
            return new ResponseEntity<>(shirts, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
