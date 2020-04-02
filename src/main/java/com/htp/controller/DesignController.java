package com.htp.controller;

import com.htp.domain.DesignShirt;
import com.htp.repository.DesignShirtRepository;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/design")
@RequiredArgsConstructor
public class DesignController {

    private final DesignShirtRepository designShirtRepo;

    @GetMapping("/color")
    public ResponseEntity<List<DesignShirt>> designShirtByColor(
            @ApiParam(value = "Color by which need to filter design tee-shirts")
            @RequestParam(name = "colorList")
                    List<String> colorList) {
        List<DesignShirt> shirts = designShirtRepo.findByColor(colorList);
        if (!shirts.isEmpty()) {
            return new ResponseEntity<>(shirts, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
