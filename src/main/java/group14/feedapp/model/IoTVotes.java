package group14.feedapp.model;

import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@UuidGenerator(name = "iotVotesIdGenerator")
public class IoTVotes {

    @Id
    @GeneratedValue(generator = "iotVotesIdGenerator")
    private String id;

    private int answerA;
    private int answerB;

    @ManyToOne
    private IoTVotingDevice votingDevice;

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

    public IoTVotingDevice getVotingDevice() {
        return votingDevice;
    }

    public void setVotingDevice(IoTVotingDevice votingDevice) {
        this.votingDevice = votingDevice;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
