package group14.feedapp.model;

import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@UuidGenerator(name = "voteIdGenerator")
public class Vote {
    @Id
    @GeneratedValue(generator = "voteIdGenerator")
    private String id;

    public enum Answer {
        ANSWER_A, ANSWER_B
    }

    private Answer answer;

    @ManyToOne
    private Poll poll;

    @ManyToOne
    private User user;

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

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
