package group14.feedapp.controller;

import group14.feedapp.exception.ErrorResponse;
import group14.feedapp.exception.NoAccessException;
import group14.feedapp.exception.ResourceNotFoundException;
import group14.feedapp.model.Poll;
import group14.feedapp.model.User;
import group14.feedapp.service.IAuthService;
import group14.feedapp.service.IPollService;
import group14.feedapp.utils.WebMapper;
import group14.feedapp.web.PollCreateRequest;
import group14.feedapp.web.PollWeb;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public ResponseEntity<List<PollWeb>> getAllPolls(@RequestHeader String userId) {
        List<Poll> polls = pollService.getAllOngoingPolls(userId);
        List<PollWeb> mappedPolls = polls.stream().map(poll -> mapper.MapPollToWeb(poll, userId)).collect(Collectors.toList());
        return ResponseEntity.ok(mappedPolls);
    }

    @GetMapping("/{pincode}")
    public ResponseEntity<Object> getPollByPincode(@RequestHeader String userId, @PathVariable("pincode") String pincode) {
        // TODO: Flytte parsing-logikk til service?
        int pinParsed = -1;
        try {
            pinParsed = Integer.parseInt(pincode);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body(
                    new ErrorResponse(400, "Bad request", String.format("Pincode '%s' is not a number.", pincode))
            );
        }

        try {
            Poll poll = pollService.getPollByPincode(pinParsed, userId);
            PollWeb pollWeb = mapper.MapPollToWeb(poll);

            return ResponseEntity.ok(pollWeb);

        } catch (ResourceNotFoundException notFoundException) {
            return ResponseEntity.status(notFoundException.getHttpStatus()).body(
                new ErrorResponse(
                    notFoundException.getHttpStatus().value(),
                    notFoundException.getHttpStatus().getReasonPhrase(),
                    String.format("Poll with pin %s doesn't exist.", pincode)
                )
            );
        } catch (NoAccessException noAccessException) {
            return ResponseEntity.status(noAccessException.getHttpStatus()).body(
                new ErrorResponse(
                    noAccessException.getHttpStatus().value(),
                    noAccessException.getHttpStatus().getReasonPhrase(),
                    String.format("User with id %s is not the owner of the poll", userId)
                )
            );
        }
    }

    @DeleteMapping("{pollId}")
    public ResponseEntity<Object> DeletePoll(@RequestHeader String userId, @PathVariable String pollId) {
        try {
            pollService.deletePoll(pollId, userId);
            return ResponseEntity.status(204).body("Poll deleted");
        } catch (ResourceNotFoundException notFoundException) {
            return ResponseEntity.status(notFoundException.getHttpStatus()).body(
                new ErrorResponse(
                    notFoundException.getHttpStatus().value(),
                    notFoundException.getHttpStatus().getReasonPhrase(),
                    String.format("The poll with id %s doesn't exist.", pollId)
                )
            );
        } catch (NoAccessException noAccessException) {
            return ResponseEntity.status(noAccessException.getHttpStatus()).body(
                new ErrorResponse(
                    noAccessException.getHttpStatus().value(),
                    noAccessException.getHttpStatus().getReasonPhrase(),
                    String.format("User with id %s is not the owner of the poll", userId)
                )
            );
        }
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



}
