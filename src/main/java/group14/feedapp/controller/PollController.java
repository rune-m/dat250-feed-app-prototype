package group14.feedapp.controller;

import group14.feedapp.exception.BaseRuntimeException;
import group14.feedapp.exception.ErrorResponse;
import group14.feedapp.model.Poll;
import group14.feedapp.model.User;
import group14.feedapp.service.IAuthService;
import group14.feedapp.service.IPollService;
import group14.feedapp.utils.IParsingUtils;
import group14.feedapp.utils.ParsingUtils;
import group14.feedapp.utils.WebMapper;
import group14.feedapp.web.PollUpdateRequest;
import group14.feedapp.web.PollCreateRequest;
import group14.feedapp.web.PollWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/poll")
public class PollController {

    @Autowired
    private IPollService pollService;

    @Autowired
    private IAuthService authService;

    private final WebMapper mapper = new WebMapper();
    private final IParsingUtils parsingUtils = new ParsingUtils();

    @GetMapping
    public ResponseEntity<List<PollWeb>> getAllPolls(@RequestHeader String token) {
        User authorizedUser = authService.getAuthorizedUser(token);

        List<Poll> polls = pollService.getAllOngoingPolls(authorizedUser.getId());
        List<PollWeb> mappedPolls = polls.stream().map(poll -> mapper.MapPollToWeb(poll, authorizedUser.getId())).collect(Collectors.toList());

        return ResponseEntity.ok(mappedPolls);
    }

    @GetMapping("/{pincode}")
    public ResponseEntity<PollWeb> getPollByPincode(@RequestHeader String token, @PathVariable("pincode") String pincode) {
        int pinParsed = parsingUtils.parseIntToString(pincode);
        User authorizedUser = authService.getAuthorizedUser(token);

        Poll poll = pollService.getPollByPincode(pinParsed, authorizedUser.getId());
        PollWeb pollWeb = mapper.MapPollToWeb(poll);

        return ResponseEntity.ok(pollWeb);
    }

    @DeleteMapping("{pollId}")
    public ResponseEntity<Object> DeletePoll(@RequestHeader String token, @PathVariable String pollId) {
        User authorizedUser = authService.getAuthorizedUser(token);
        pollService.deletePoll(pollId, authorizedUser.getId());
        return ResponseEntity.status(204).body("Poll deleted");
    }

    @PostMapping
    public ResponseEntity<Object> createPoll(@RequestHeader String token,
                                             @RequestBody PollCreateRequest request){
        User authorizedUser = authService.getAuthorizedUser(token);
        Poll poll = mapper.MapPollCreateRequestToPoll(request);
        poll.setUser(authorizedUser);
        Poll response = pollService.createPoll(poll);
        return (response != null) ? ResponseEntity.ok(mapper.MapPollToWeb(poll)) :
                                ResponseEntity.status(400).body(new ErrorResponse(400, "Bad request", "Could not save poll"));
    }

    @PutMapping("{pincode}")
    public ResponseEntity<PollWeb> updatePoll(@RequestHeader String token, @RequestBody PollUpdateRequest poll, @PathVariable String pincode) {
        int pinParsed = parsingUtils.parseIntToString(pincode);
        User authorizedUser = authService.getAuthorizedUser(token);

        Poll mappedPoll = mapper.MapPollUpdateRequestToInternal(poll, pinParsed, authorizedUser);
        Poll updatedPoll = pollService.updatePoll(mappedPoll, authorizedUser);

        return ResponseEntity.ok(mapper.MapPollToWeb(updatedPoll));
    }

}
