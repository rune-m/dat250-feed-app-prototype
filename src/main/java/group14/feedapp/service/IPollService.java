package group14.feedapp.service;

import group14.feedapp.model.Poll;

import java.util.List;

public interface IPollService {

    Poll getPollById(String id);
    Poll getPollByPincode(int pincode, String userId);
    List<Poll> getAllOngoingPolls(String userId);

    String createPoll(Poll poll);


}
