package group14.feedapp.web;

import group14.feedapp.enums.Answer;

public class VoteCreateRequestWeb {
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
