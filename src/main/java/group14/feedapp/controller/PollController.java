package group14.feedapp.controller;

import group14.feedapp.exception.ErrorResponse;
import group14.feedapp.model.Poll;
import group14.feedapp.service.IPollService;
import group14.feedapp.utils.WebMapper;
import group14.feedapp.web.PollWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/poll")
public class PollController {

    @Autowired
    private IPollService pollService;

    private WebMapper mapper = new WebMapper();

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

        Poll poll = pollService.getPollByPincode(pinParsed, userId);
        if (poll == null) return ResponseEntity.status(404).body(
                new ErrorResponse(404, "Not found", String.format("Poll with pin %s doesn't exist.", pincode))
        );

        PollWeb pollWeb = mapper.MapPollToWeb(poll);

        return ResponseEntity.ok(pollWeb);
    }

/*    @PostMapping("/{pincode}/{question}/{answerA}/{answerB}/{answerB}/{isPrivate}/{startDate}/{endDate}/{isClosed}")
    public ResponseEntity<Object> createPoll(@RequestHeader("userID") String userID,
                                             @PathVariable("pincode") String pincode,
                                             @PathVariable("question") String question,
                                             @PathVariable("answerA") String answerA,
                                             @PathVariable("answerB") String answerB,
                                             @PathVariable("isPrivate") String isPrivate,
                                             @PathVariable("startDate") String startDate,
                                             @PathVariable("endDate") String endDate,
                                             @PathVariable("isClosed")String isClosed){
        //Might be easier to request body instead of all these parameters!!!
        String response = pollService.createPoll(pincode,
                                                question,
                                                answerA,
                                                answerB,
                                                isPrivate,
                                                startDate,
                                               endDate,
                                                isClosed,
                                                userID);
        if (response == "SUCCESS"){return ResponseEntity.ok(response);}
        else {
            return ResponseEntity.status(400).body(
                new ErrorResponse(400, "Bad request", response));
        }
    }*/

    @PostMapping
    public ResponseEntity<Object> createPoll(@RequestBody Poll poll){
        System.out.println(poll.getId());
        String response = pollService.createPoll(poll);
        if (response == "SUCCESS"){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(400).body(
                new ErrorResponse(400, "Bad request", response));

    }



}
