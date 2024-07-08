package com.ohs.integration.controller;

import com.ohs.integration.dto.UserDto;
import com.ohs.integration.service.SupplierService;
import com.ohs.integration.service.UserService;
import com.ohs.integration.util.EntityMessageMapper;
import com.ohs.supplier.SupplierResponse;
import com.ohs.user.CreateUserRequest;
import com.ohs.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "{userPid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse getUser(@PathVariable String userPid) {
        return this.userService.getUser(userPid);
    }

    @PostMapping(consumes =  MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse createUser(@RequestBody UserDto userDto) {
        System.out.println("here");

        return this.userService.createUser(EntityMessageMapper.userDtoToCreateUserRequest(userDto));
    }

}
