package com.vangelis.controller;

import com.vangelis.domain.Employee;
import com.vangelis.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping({"/user"})
public class UserController
{
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    IUserService userService;

    @RequestMapping(
            value = {"/{id}"},
            method = {RequestMethod.GET}
    )
    public ResponseEntity<Employee> getUser(@PathVariable Long id) throws Exception {
        Employee user = this.userService.prueba();

        return new ResponseEntity(user, HttpStatus.OK);
    }
}
