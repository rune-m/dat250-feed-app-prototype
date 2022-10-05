package group14.feedapp.service;

import group14.feedapp.model.Poll;

public interface IPollService {

    Poll getPollById(String id);
    Poll getPollByPincode(int pincode);

}
