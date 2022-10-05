package group14.feedapp.controller;

import group14.feedapp.model.User;
import group14.feedapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.status(404).body(null);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.ok(newUser);
    }

}
