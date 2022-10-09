package group14.feedapp.web;

import group14.feedapp.enums.Answer;

public class VoteWeb {
    private String id;
    private Answer answer;
    private PollWeb poll;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
    public PollWeb getPoll() {return poll;}
    public void setPoll(PollWeb poll) {this.poll = poll;}
}
