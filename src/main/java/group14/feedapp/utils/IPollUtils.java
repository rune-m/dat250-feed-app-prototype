package group14.feedapp.utils;

import group14.feedapp.model.Poll;
import org.apache.commons.lang3.tuple.Pair;

public interface IPollUtils {

    Pair<Integer, Integer> countPollVotes(Poll poll);

}
