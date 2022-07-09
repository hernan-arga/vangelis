package com.vangelis.controller;

import com.vangelis.domain.Employee;
import com.vangelis.domain.User;
import com.vangelis.service.IUserService;
import com.vangelis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/user"})
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    IUserService userService;

    public UserController() {
        try {
            userService = new UserService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(
            value = {"/{id}"},
            method = {RequestMethod.GET}
    )
    public ResponseEntity<Employee> getUser(@PathVariable Long id) throws Exception {
        Employee user = this.userService.prueba();
        //User user = this.userService.getUser(id);
        return new ResponseEntity(user, HttpStatus.OK);
    }
}
