package org.perez.maintenance.users.controller;

import jakarta.validation.Valid;
import org.perez.maintenance.users.model.User;
import org.perez.maintenance.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService) {
        this.userService = userService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/api/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") Long id) {
        Optional<User> userOptional = userService.findById(id);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.get());
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/users")
    public ResponseEntity<?> save(@Valid @RequestBody User user, BindingResult result) {

        if(!user.getLogin().isEmpty() && userService.findByLogin(user.getLogin()).isPresent()){
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("message", "Login already exists"));
        }

        // error handling
        if(result.hasErrors()){
            return validate(result);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {

        // error handling
        if(result.hasErrors()){
            return validate(result);
        }

        Optional<User> userOptional = userService.findById(id);
        if(userOptional.isPresent()){
            User userDb = userOptional.get();

            if(!user.getLogin().isEmpty() &&
                    !user.getLogin().equalsIgnoreCase(userDb.getLogin()) &&
                    userService.findByLogin(user.getLogin()).isPresent()){
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("message", "Login already exists"));
            }

            userDb.setName(user.getName());
            userDb.setLastName(user.getLastName());
            userDb.setLogin(user.getLogin());
            if(!user.getPassword().isEmpty()) {
                userDb.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userDb.setState(user.getState());

            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDb));
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    private static ResponseEntity<Map<String, Object>> validate(BindingResult result) {
        Map<String, Object> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "The field " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
