package group14.feedapp.controller;

import group14.feedapp.model.DeviceVote;
import group14.feedapp.model.User;
import group14.feedapp.model.Vote;
import group14.feedapp.service.IAuthService;
import group14.feedapp.service.IVoteService;
import group14.feedapp.utils.WebMapper;
import group14.feedapp.web.DeviceVoteCreateRequestWeb;
import group14.feedapp.web.DeviceVoteWeb;
import group14.feedapp.web.VoteCreateRequestWeb;
import group14.feedapp.web.VoteWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vote")
public class VoteController {
    @Autowired
    private IAuthService authService;
    @Autowired
    private IVoteService voteService;

    private final WebMapper mapper = new WebMapper();

    @PostMapping
    public ResponseEntity<VoteWeb> createVote(
            @RequestHeader(required = false) String token,
            @RequestBody VoteCreateRequestWeb voteRequest
    ) {
        User authorizedUser;

        if (token == null || token.isEmpty() || token.trim().isBlank()) {
            authorizedUser = null;
        } else {
            authorizedUser = authService.getAuthorizedUser(token);
        }

        var vote = mapper.MapVoteRequestToInternal(voteRequest);

        Vote createdVote = voteService.createVote(vote, authorizedUser);
        return ResponseEntity.ok(mapper.MapVoteToWeb(createdVote));
    }

    @PostMapping("/device")
    public ResponseEntity<DeviceVoteWeb> createDeviceVote(
            @RequestHeader String deviceId,
            @RequestBody DeviceVoteCreateRequestWeb deviceVoteRequest) {
        var deviceVote = mapper.MapDeviceVoteRequestToInternal(deviceVoteRequest);

        DeviceVote createdVote = voteService.createDeviceVote(deviceVote, deviceId);
        return ResponseEntity.ok(mapper.MapDeviceVoteToWeb(createdVote));
    }
}
