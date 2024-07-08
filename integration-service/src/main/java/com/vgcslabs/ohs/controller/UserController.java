package com.vgcslabs.ohs.controller;

import com.vgcslabs.ohs.dto.UserDto;
import com.vgcslabs.ohs.service.UserService;
import com.vgcslabs.ohs.util.EntityMessageMapper;
import com.vgcslabs.user.UserResponse;
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
