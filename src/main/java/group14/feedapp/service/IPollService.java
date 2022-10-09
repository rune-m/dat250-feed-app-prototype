package group14.feedapp.service;

import group14.feedapp.exception.NoAccessException;
import group14.feedapp.exception.ResourceNotFoundException;
import group14.feedapp.model.Poll;

import java.util.List;

public interface IPollService {

    Poll getPollById(String id);
    Poll getPollByPincode(int pincode, String userId) throws NoAccessException, ResourceNotFoundException;
    List<Poll> getAllOngoingPolls(String userId);
    void deletePoll(String pollId, String userId) throws NoAccessException, ResourceNotFoundException;

    String createPoll(Poll poll);


}
