package com.htp.controller;

import com.htp.controller.requests.UserUpdateRequest;
import com.htp.domain.User;
import com.htp.repository.UserRepository;
import com.htp.security.Headers;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

  private final UserRepository userRepo;
  private final ConversionService conversionService;

  @ApiOperation(
      value = "Full update selected user", notes ="Can't update username, password and roles")
  @ApiResponses({
    @ApiResponse(code = 200, message = "User is updated"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @ApiImplicitParams({
    @ApiImplicitParam(name = Headers.AUTH_TOKEN, value = "token", required = true, dataType = "string", paramType = "header")
  })
  @Transactional(rollbackOn = Exception.class)
  @PutMapping("/user")
  public ResponseEntity<User> updateUser(
      @RequestBody @Valid UserUpdateRequest request) {
    User convertedUser = conversionService.convert(request, User.class);
    return new ResponseEntity<>(userRepo.saveAndFlush(convertedUser), HttpStatus.OK);
  }

  @ApiOperation(value = "Update user's role", notes = "This can only be done by admin")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful updating role"),
    @ApiResponse(code = 400, message = "Invalid parameter value"),
    @ApiResponse(code = 404, message = "User not found"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @ApiImplicitParams({
    @ApiImplicitParam(name = Headers.AUTH_TOKEN, value = "token", required = true, dataType = "string", paramType = "header")
  })
  @Transactional(rollbackOn = Exception.class)
  @PutMapping("/admin/user/role/{id}")
  public ResponseEntity<User> updateRole(
      @ApiParam(value = "Id of user that need updated role") @PathVariable("id") String id,
      @ApiParam(value = "New role id") @RequestParam String roleId) {
    try {
      long userId = Long.valueOf(id);
      Optional<User> userOptional = userRepo.findById(userId);
      if (userOptional.isPresent()) {
        userRepo.updateRole(Long.valueOf(roleId), userId);
        userRepo.flush();
        return new ResponseEntity<>(userRepo.findById(userId).get(), HttpStatus.OK);
      }
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } catch (NumberFormatException ex) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  @ApiOperation(value = "Delete user by id", notes = "This can only be done by admin")
  @ApiResponses({
    @ApiResponse(code = 204, message = "Successful deleting user"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @ApiImplicitParams({
    @ApiImplicitParam(name = Headers.AUTH_TOKEN, value = "token", required = true, dataType = "string", paramType = "header")
  })
  @Transactional(rollbackOn = Exception.class)
  @DeleteMapping("/admin/user/{id}")
  public ResponseEntity<User> deleteUser(
      @ApiParam(value = "Id of user that need to be deleted") @PathVariable("id") String id) {
    User user = userRepo.findById(Long.valueOf(id)).orElseThrow(EntityNotFoundException::new);
    user.setDeleted(true);
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  @ApiOperation(value = "Find user by id", notes = "This can only be done by admin")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful getting user"),
    @ApiResponse(code = 404, message = "User with such id not found"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @ApiImplicitParams({
    @ApiImplicitParam(name = Headers.AUTH_TOKEN, value = "token", required = true, dataType = "string", paramType = "header" )
  })
  @GetMapping("/admin/user/{id}")
  public ResponseEntity<User> userById(
      @ApiParam(value = "Id of user that need to be found") @PathVariable("id") Long id) {
    Optional<User> userById = userRepo.findByIdAndDeletedFalse(id);
    return userById
        .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
  }

  @ApiOperation(value = "Find all users that not deleted", notes = "This can only be done by admin")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful getting users"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @ApiImplicitParams({
    @ApiImplicitParam(name = Headers.AUTH_TOKEN, value = "token", required = true, dataType = "string", paramType = "header")
  })
  @GetMapping("/admin/user/all")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userRepo.findByDeletedFalse();
    if (!users.isEmpty()) {
      return new ResponseEntity<>(users, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }
}
