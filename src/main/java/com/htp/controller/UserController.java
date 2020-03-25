package com.htp.controller;

import com.htp.controller.requests.UserCreateRequest;
import com.htp.domain.User;
import com.htp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepo;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userRepo.findById(id), HttpStatus.OK);
    }

    @PostMapping("/user")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@RequestBody @Valid UserCreateRequest request) {
        User user = new User();

        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        user.setFullName(request.getFullName());
        user.setBirthDate(request.getBirthDate());
        user.setCity(request.getCity());
        user.setStreet(request.getStreet());
        user.setZip(request.getZip());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setMail(request.getMail());

        return new ResponseEntity<>(userRepo.save(user), HttpStatus.OK);
    }

}
