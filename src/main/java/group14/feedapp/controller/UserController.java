package group14.feedapp.controller;

import group14.feedapp.model.User;
import group14.feedapp.service.IUserService;
import group14.feedapp.utils.WebMapper;
import group14.feedapp.web.UserWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService userService;
    private WebMapper mapper = new WebMapper();

    @GetMapping("/{id}")
    public ResponseEntity<UserWeb> getUserById(@PathVariable("id") String id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(mapper.MapUserToWeb(user)) : ResponseEntity.status(404).body(null);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.ok(newUser);
    }

}
