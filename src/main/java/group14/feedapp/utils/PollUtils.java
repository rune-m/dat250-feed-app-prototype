package group14.feedapp.utils;

import group14.feedapp.enums.Answer;
import group14.feedapp.model.Poll;
import group14.feedapp.model.Vote;
import org.apache.commons.lang3.tuple.Pair;

import java.util.concurrent.atomic.AtomicInteger;

public class PollUtils implements IPollUtils {

    @Override
    public Pair<Integer, Integer> countPollVotes(Poll poll) {
        AtomicInteger countA = new AtomicInteger();
        AtomicInteger countB = new AtomicInteger();
        poll.getVotes().stream().forEach(vote -> {
            if (vote.getAnswer() == Answer.ANSWER_A)
                countA.getAndIncrement();
            else if (vote.getAnswer() == Answer.ANSWER_B)
                countB.getAndIncrement();
        });
        return Pair.of(countA.get(), countB.get());
    }
}
