package group14.feedapp.service;

import group14.feedapp.model.Poll;
import group14.feedapp.model.User;

import java.time.LocalDateTime;

import java.util.List;

public interface IPollService {

    Poll getPollById(String id);
    Poll getPollByPincode(int pincode, String userId);
    List<Poll> getAllOngoingPolls(String userId);

    String createPoll(Poll poll);


}
