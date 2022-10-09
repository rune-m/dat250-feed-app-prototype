package group14.feedapp.controller;

import group14.feedapp.model.IoTVote;
import group14.feedapp.model.Vote;
import group14.feedapp.service.IAuthService;
import group14.feedapp.service.IVoteService;
import group14.feedapp.utils.WebMapper;
import group14.feedapp.web.IoTVoteCreateRequestWeb;
import group14.feedapp.web.IoTVoteWeb;
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

    private WebMapper mapper = new WebMapper();

    @PostMapping
    public ResponseEntity<VoteWeb> createVote(
            @RequestHeader String token,
            @RequestBody VoteCreateRequestWeb voteRequest
    ) {

        var autherizedUser = authService.getAuthorizedUser(token);
        var vote = mapper.MapVoteRequestToInternal(voteRequest);

        Vote createdVote = voteService.createVote(vote, autherizedUser);
        return ResponseEntity.ok(mapper.MapVoteToWeb(createdVote));
    }

    @PostMapping("/device")
    public ResponseEntity<IoTVoteWeb> createIoTVotes(
            @RequestHeader String deviceId,
            @RequestBody IoTVoteCreateRequestWeb iotVoteRequest) {
        var deviceVote = mapper.MapIoTVoteRequestToInternal(iotVoteRequest);

        IoTVote createdVote = voteService.createDeviceVote(deviceVote, deviceId);
        return ResponseEntity.ok(mapper.MapDeviceVoteToWeb(createdVote));
    }
}
