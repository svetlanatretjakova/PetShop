package com.example.petshop.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//We use RESTful webservice for responsiveness of the application - meaning that the users can see the response immediate without seeing page reloaded.
//
//In case of checking email uniqueness, the application will show a duplicate email warning
// in a decent way (a modal dialog).
// If we use standard form submit process, then the page will be reloaded and the password field will be reset
// (the user will have to enter password again - which is not convenient)
// - and some other fields will lose user-entered values as well on page reload.

//use REST-AJAX in places where page reload would be inconvenient for the end users.
@RestController
//the @RequestParam annotation can be used to map request parameter
// in HTTP body to method arguments.
// And it is optional. No need to use @RequestParam for basic data types like String, Integer, Long
public class UserRestController {
    @Autowired
    private UserService service;

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email) {
        return service.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }
}
