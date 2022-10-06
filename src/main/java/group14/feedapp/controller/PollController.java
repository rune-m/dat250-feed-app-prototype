package group14.feedapp.controller;

import group14.feedapp.exception.ErrorResponse;
import group14.feedapp.model.Poll;
import group14.feedapp.model.User;
import group14.feedapp.service.IPollService;
import group14.feedapp.utils.WebMapper;
import group14.feedapp.web.PollWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/poll")
public class PollController {

    @Autowired
    private IPollService pollService;

    private WebMapper mapper = new WebMapper();

    @GetMapping("/{pincode}")
    public ResponseEntity<Object> getPollByPincode(@PathVariable("pincode") String pincode) {
        // TODO: Flytte parsing-logikk til service?
        int pinParsed = -1;
        try {
            pinParsed = Integer.parseInt(pincode);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body(
                    new ErrorResponse(400, "Bad request", String.format("Pincode '%s' is not a number.", pincode))
            );
        }

        Poll poll = pollService.getPollByPincode(pinParsed);
        if (poll == null) return ResponseEntity.status(404).body(
                new ErrorResponse(404, "Not found", String.format("Poll with pin %s doesn't exist.", pincode))
        );

        PollWeb pollWeb = mapper.MapPollToWeb(poll);

        return ResponseEntity.ok(pollWeb);
    }

    @PostMapping()
    public ResponseEntity<Object> createPoll(@RequestBody String pincode,
                                             String question,
                                             String answerA,
                                             String answerB,
                                             String isPrivate,
                                             String startDate,
                                             String endDate,
                                             String isClosed,
                                             String userID){

        return (createdPoll != null) ? ResponseEntity.ok(createdPoll): ResponseEntity.status(404).body(
                                                                        new ErrorResponse(404,
                                                                                "Already exist",
                                                                                "Poll already exist"));
    }



}
