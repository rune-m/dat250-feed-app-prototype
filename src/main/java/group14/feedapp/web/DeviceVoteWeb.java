package group14.feedapp.web;

public class DeviceVoteWeb {

    private String id;
    private int answerA;
    private int answerB;
    private PollWeb poll;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAnswerA() {
        return answerA;
    }

    public void setAnswerA(int answerA) {
        this.answerA = answerA;
    }

    public int getAnswerB() {
        return answerB;
    }

    public void setAnswerB(int answerB) {
        this.answerB = answerB;
    }

    public PollWeb getPoll() {
        return poll;
    }

    public void setPoll(PollWeb poll) {
        this.poll = poll;
    }
}
