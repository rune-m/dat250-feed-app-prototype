package group14.feedapp.web;

import java.time.LocalDateTime;

public class PollWeb {

    private String id;
    private int pincode;
    private String question;
    private String answerA;
    private String answerB;
    private String userId;
    private int answerACount;
    private int answerBCount;
    private boolean isPrivate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isClosed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAnswerACount() {
        return answerACount;
    }

    public void setAnswerACount(int answerACount) {
        this.answerACount = answerACount;
    }

    public int getAnswerBCount() {
        return answerBCount;
    }

    public void setAnswerBCount(int answerBCount) {
        this.answerBCount = answerBCount;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
