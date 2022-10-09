package group14.feedapp.controller;

import group14.feedapp.service.IoTDeviceService;
import group14.feedapp.service.PollService;
import group14.feedapp.service.UserService;
import group14.feedapp.web.VoteWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vote")
public class VoteController {

    @Autowired
    private UserService userService;

    @Autowired
    private IoTDeviceService deviceService;

    @Autowired
    private PollService pollService;

    @PostMapping("/{pollId}")
    public ResponseEntity<VoteWeb> vote(
            @RequestHeader("Authorization") String token,
            @PathVariable("pollId") String pollId) {

    }
}
