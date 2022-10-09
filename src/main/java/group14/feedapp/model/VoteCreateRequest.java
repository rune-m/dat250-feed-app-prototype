package group14.feedapp.model;

import group14.feedapp.enums.Answer;

import java.util.UUID;

public class VoteCreateRequest {
    private Answer answer;

    private String pollId;


    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
