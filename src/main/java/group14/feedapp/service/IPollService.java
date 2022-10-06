package group14.feedapp.service;

import group14.feedapp.model.Poll;
import group14.feedapp.model.User;

import java.time.LocalDateTime;

public interface IPollService {

    Poll getPollById(String id);
    Poll getPollByPincode(int pincode);

    String createPoll(String pincode,
                      String question,
                      String answerA,
                      String answerB,
                      String isPrivate,
                      String startDate,
                      String endDate,
                      String isClosed,
                      String userID);

    int parseInt(String number);

    boolean parseBool(String bool);

    LocalDateTime parseDateTime(String dateTime);

}
