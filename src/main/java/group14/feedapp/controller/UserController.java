package group14.feedapp.controller;

import group14.feedapp.model.User;
import group14.feedapp.service.IUserService;
import group14.feedapp.utils.WebMapper;
import group14.feedapp.web.UserCreateRequest;
import group14.feedapp.web.UserWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/all/{id}")
    public ResponseEntity<List<UserWeb>> getAllUsers(@PathVariable("id") String id) {
        User user = userService.getUserById(id);
        if (user == null){return ResponseEntity.status(404).body(null);}
        List<User> users = userService.getAllUsers(user);
        return users != null ?
                ResponseEntity.ok(users.stream().map(usr -> mapper.MapUserToWeb(usr)).collect(Collectors.toList())) :
                ResponseEntity.status(403).body(null);
    }

    @PostMapping
    public ResponseEntity<UserWeb> createUser(@RequestBody UserCreateRequest user) {
        User newUser = userService.createUser(mapper.getMapper().map(user, User.class));
        return ResponseEntity.ok(mapper.MapUserToWeb(newUser));
    }





}
