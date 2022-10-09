package group14.feedapp.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class IoTVote {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private int answerA;
    private int answerB;

    @ManyToOne
    private IoTDevice votingDevice;

    @ManyToOne
    private Poll poll;

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

    public IoTDevice getVotingDevice() {
        return votingDevice;
    }

    public void setVotingDevice(IoTDevice votingDevice) {
        this.votingDevice = votingDevice;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
